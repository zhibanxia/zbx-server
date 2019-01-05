package cn.zhibanxia.zbxserver.controller.rest;

import cn.zhibanxia.zbxserver.bo.ListRecycleRequestBo;
import cn.zhibanxia.zbxserver.constant.ErrorCode;
import cn.zhibanxia.zbxserver.controller.param.Addr;
import cn.zhibanxia.zbxserver.controller.param.CompleteRecycleReq;
import cn.zhibanxia.zbxserver.controller.param.ComplexVo;
import cn.zhibanxia.zbxserver.controller.param.ConfirmRecycleReq;
import cn.zhibanxia.zbxserver.controller.param.DeleteRecycleReq;
import cn.zhibanxia.zbxserver.controller.param.RecycleRequestVo;
import cn.zhibanxia.zbxserver.controller.param.Result;
import cn.zhibanxia.zbxserver.entity.ComplexEntity;
import cn.zhibanxia.zbxserver.entity.RecycleRequestEntity;
import cn.zhibanxia.zbxserver.entity.UserAddressEntity;
import cn.zhibanxia.zbxserver.entity.UserEntity;
import cn.zhibanxia.zbxserver.filter.RequestLocal;
import cn.zhibanxia.zbxserver.service.ComplexService;
import cn.zhibanxia.zbxserver.service.RecycleRequestService;
import cn.zhibanxia.zbxserver.service.UserAddrService;
import cn.zhibanxia.zbxserver.service.UserService;
import cn.zhibanxia.zbxserver.util.BeanUtil;
import cn.zhibanxia.zbxserver.util.DateUtil;
import cn.zhibanxia.zbxserver.util.LoggerUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by zzy on  2018/10/02 14:48
 */
@RestController
@RequestMapping("/rest/recycle")
public class RecycleCtrl {
    private static Logger logger = LoggerFactory.getLogger(RecycleCtrl.class);
    private static Logger adminAccessLogger = LoggerUtil.getAdminAccessLogger();
    @Autowired
    private RecycleRequestService recycleRequestService;
    @Autowired
    private UserAddrService userAddrService;
    @Autowired
    private UserService userService;
    @Autowired
    private ComplexService complexService;

    private final Cache<Long, AtomicInteger> createOrderCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).maximumSize(2000).build();

    /**
     * 单个用户每分钟最多创建的回收请求个数
     */
    private static final int MAX_CREATE_QPM = 5;

    /**
     * 获取回收列表，根据参数不同，展示不同的数据：
     * bizType
     * 1.回收人员查看待回收列表
     * 2.回收人员查询已经确认的列表
     * 3.业主查询自己提交的回收请求
     *
     * @return
     */
    @GetMapping("list")
    public Result<List<RecycleRequestVo>> list(@RequestParam("bizType") Integer bizType, @RequestParam("page") Integer page,
                                               @RequestParam(value = "size", required = false) Integer size) {
        if (Objects.equals(1, bizType) || Objects.equals(2, bizType)) {
            // 必须是回收人员，并且通过了认证
            if (!RequestLocal.get().isHuishou() || !Objects.equals(UserEntity.USER_STATUS_NORMAL, RequestLocal.get().getHuishouUserEntity().getUserStatus())) {
                return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
            }
        }

        // 业务类型3必须是业主
        if (Objects.equals(3, bizType)) {
            if (!RequestLocal.get().isYezhu()) {
                return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
            }
        }

        if (Objects.equals(1, bizType)) {
            ListRecycleRequestBo listRecycleRequestBo = new ListRecycleRequestBo();
            listRecycleRequestBo.setResStatus(RecycleRequestEntity.RES_STATUS_PUBLISH);
            listRecycleRequestBo.setDeleted(false);
            List<Long> focusComplexIdList = userAddrService.findFocusAddrs(RequestLocal.get().getHuishouUid()).stream().map(UserAddressEntity::getComplexId).collect(Collectors.toList());
            listRecycleRequestBo.setFocusComplexIdList(focusComplexIdList);
            return Result.ResultBuilder.success(buildRecycleRequestVoList(listRecycleRequestBo, page, size, true));
        } else if (Objects.equals(2, bizType)) {
            ListRecycleRequestBo listRecycleRequestBo = new ListRecycleRequestBo();
            listRecycleRequestBo.setRecycleUserId(RequestLocal.get().getHuishouUid());
            listRecycleRequestBo.setDeleted(false);
            return Result.ResultBuilder.success(buildRecycleRequestVoList(listRecycleRequestBo, page, size, false));
        } else if (Objects.equals(3, bizType)) {
            ListRecycleRequestBo listRecycleRequestBo = new ListRecycleRequestBo();
            listRecycleRequestBo.setCreateUserId(RequestLocal.get().getYezhuUid());
            listRecycleRequestBo.setDeleted(false);
            return Result.ResultBuilder.success(buildRecycleRequestVoList(listRecycleRequestBo, page, size, false));
        } else {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
    }

    /**
     * 获取回收列表，根据参数不同，展示不同的数据：
     * bizType
     * 1.回收人员查看待回收列表
     * 2.回收人员查询已经确认的列表
     * 3.业主查询自己提交的回收请求
     *
     * @return
     */
    @GetMapping("list4Admin")
    public Result<List<RecycleRequestVo>> list4Admin(@RequestParam(value = "status", required = false) Integer status, @RequestParam("page") Integer page,
                                                     @RequestParam(value = "size", required = false) Integer size) {
        // 必须是回收人员，并且通过了认证
        if (!RequestLocal.get().isAdmin()) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        try {
            ListRecycleRequestBo listRecycleRequestBo = new ListRecycleRequestBo();
            if (!RecycleRequestEntity.RES_TYPES.contains(status)) {
                status = null;
            }
            if (status != null) {
                listRecycleRequestBo.setResStatus(status);
            }
            listRecycleRequestBo.setDeleted(false);
            return Result.ResultBuilder.success(buildRecycleRequestVoList(listRecycleRequestBo, page, size, true));
        } finally {
            adminAccessLogger.info("uid={}|it={}|param={}", RequestLocal.get().getAdminUid(), "list4Admin", "status=" + status + ", page=" + page + ", size=" + size);
        }
    }

    @GetMapping("detail")
    public Result<RecycleRequestVo> getRecyleRequestDetail(@RequestParam("id") Long id, @RequestParam(value = "bizType", required = false) Integer bizType) {
        if (Objects.equals(1, bizType) || Objects.equals(2, bizType)) {
            // 必须是回收人员，并且通过了认证
            if (!RequestLocal.get().isHuishou() || !Objects.equals(UserEntity.USER_STATUS_NORMAL, RequestLocal.get().getHuishouUserEntity().getUserStatus())) {
                return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
            }
        }
        // 业务类型3必须是业主
        if (Objects.equals(3, bizType)) {
            if (!RequestLocal.get().isYezhu()) {
                return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
            }
        }
        if (bizType == null) {
            if (!RequestLocal.get().isAdmin()) {
                return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
            }
        }
        RecycleRequestEntity e = recycleRequestService.find(id);
        if (e == null) {
            return Result.ResultBuilder.success(null);
        }
        if (Objects.equals(1, bizType)) {
            boolean needShow = RequestLocal.get().getHuishouUid() != null && RequestLocal.get().getHuishouUid().equals(e.getRecycleUserId());
            return Result.ResultBuilder.success(buildRecycleRequestVo(e, null, !needShow));
        } else if (Objects.equals(2, bizType)) {
            // 不允许查询非自己确认的回收请求
            if (!RequestLocal.get().getHuishouUid().equals(e.getRecycleUserId())) {
                return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
            }
            return Result.ResultBuilder.success(buildRecycleRequestVo(e, null, false));
        } else if (Objects.equals(3, bizType)) {
            // 不允许查询非自己创建的回收请求
            if (!RequestLocal.get().getYezhuUid().equals(e.getCreateUserId())) {
                return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
            }
            return Result.ResultBuilder.success(buildRecycleRequestVo(e, null, false));
        } else if (bizType == null) {
            return Result.ResultBuilder.success(buildRecycleRequestVo(e, null, false));
        } else {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
    }

    @PostMapping("create")
    public Result<Long> create(@RequestBody RecycleRequestVo recycleRequestVo) {
        if (!RequestLocal.get().isYezhu()) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        if (!(checkParam(recycleRequestVo))) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_INVALID_PARAM_ERROR);
        }
        AtomicInteger count;
        try {
            count = createOrderCache.get(RequestLocal.get().getYezhuUid(), () -> new AtomicInteger(0));
            if (count.get() > MAX_CREATE_QPM) {
                return Result.ResultBuilder.fail(ErrorCode.CODE_CREATE_FREQUENT_LIMIT);
            }
        } catch (Exception e) {
            logger.warn("", e);
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNKONWN_ERROR);
        }
        try {
            RecycleRequestEntity recycleRequestEntity = convertVo2Entity(recycleRequestVo);
            recycleRequestEntity.setCreateUserId(RequestLocal.get().getYezhuUid());
            recycleRequestEntity.setResStatus(RecycleRequestEntity.RES_STATUS_PUBLISH);

            // 回收请求的地址保存下来，下次用
            UserAddressEntity userAddressEntity = new UserAddressEntity();
            userAddressEntity.setBizType(UserAddressEntity.BIZ_TYPE_YEZHU);
            userAddressEntity.setUserId(RequestLocal.get().getYezhuUid());
            userAddressEntity.setComplexId(recycleRequestEntity.getComplexId());
            userAddressEntity.setDoorInfo(recycleRequestEntity.getDoorInfo());
            userAddrService.addOrUpdateOnlyAddr(userAddressEntity);

            // 保存业主手机号，方便下次提交时不用再填
            userService.addMobileAndVerify(RequestLocal.get().getYezhuUid(), recycleRequestEntity.getMobilePhone(), null, null, null);

            Long id = recycleRequestService.create(recycleRequestEntity);
            // 下单成功则计数次数+1;
            count.incrementAndGet();

            return Result.ResultBuilder.success(id);
        } catch (Exception e) {
            logger.warn("", e);
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNKONWN_ERROR);
        }
    }

    @PostMapping("confirmRecycle")
    public Result<Void> confirmRecycle(@RequestBody ConfirmRecycleReq confirmRecycleReq) {
        // 如果不是回收人员、或者状态不是正常状态，则拒绝请求
        if (!RequestLocal.get().isHuishou() || !Objects.equals(RequestLocal.get().getHuishouUserEntity().getUserStatus(), UserEntity.USER_STATUS_NORMAL)) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        Long id = confirmRecycleReq.getId();
        RecycleRequestEntity recycleRequestEntity = recycleRequestService.find(id);
        if (recycleRequestEntity == null) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_INVALID_PARAM_ERROR);
        }
        if (!Objects.equals(RecycleRequestEntity.RES_STATUS_PUBLISH, recycleRequestEntity.getResStatus())) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_RECYCLE_HAS_HANDLED_ERROR);
        }
        boolean success = recycleRequestService.confirmRecycleRequest(id, RequestLocal.get().getHuishouUid());
        if (!success) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_RECYCLE_HAS_HANDLED_ERROR);
        }
        return Result.ResultBuilder.success(null);
    }

    @PostMapping("delete")
    public Result<Boolean> delete(@RequestBody DeleteRecycleReq deleteRecycleReq) {
        // 如果不是业主则拒绝请求
        if (!RequestLocal.get().isYezhu()) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        if (deleteRecycleReq == null || deleteRecycleReq.getId() == null) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_INVALID_PARAM_ERROR);
        }
        Long id = deleteRecycleReq.getId();
        RecycleRequestEntity recycleRequestEntity = recycleRequestService.find(id);
        if (recycleRequestEntity == null) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_INVALID_PARAM_ERROR);
        }
        if (!Objects.equals(RecycleRequestEntity.RES_STATUS_PUBLISH, recycleRequestEntity.getResStatus())) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_RECYCLE_HAS_HANDLED_ERROR);
        }
        return Result.ResultBuilder.success(recycleRequestService.delete(id));
    }

    @PostMapping("update")
    public Result<Boolean> update(@RequestBody RecycleRequestVo recycleRequestVo) {
        // 如果不是业主则拒绝请求
        if (!RequestLocal.get().isYezhu()) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        if (recycleRequestVo == null) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_INVALID_PARAM_ERROR);
        }
        RecycleRequestEntity recycleRequestEntity = recycleRequestService.find(recycleRequestVo.getId());
        if (recycleRequestEntity == null) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_INVALID_PARAM_ERROR);
        }
        if (!Objects.equals(RecycleRequestEntity.RES_STATUS_PUBLISH, recycleRequestEntity.getResStatus())) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_RECYCLE_HAS_HANDLED_ERROR);
        }
        try {
            RecycleRequestEntity updateEntity = convertVo2Entity(recycleRequestVo);
            updateEntity.setId(recycleRequestVo.getId());
            return Result.ResultBuilder.success(recycleRequestService.update(updateEntity));
        } catch (Exception e) {
            logger.warn("", e);
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNKONWN_ERROR);
        }
    }

    @PostMapping("completeRecycle")
    public Result<Boolean> completeRecycle(@RequestBody CompleteRecycleReq completeRecycleReq) {
        // 如果不是业主则拒绝请求
        if (!RequestLocal.get().isYezhu()) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        if (completeRecycleReq == null || completeRecycleReq.getId() == null) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_INVALID_PARAM_ERROR);
        }
        Long id = completeRecycleReq.getId();
        RecycleRequestEntity recycleRequestEntity = recycleRequestService.find(id);
        if (recycleRequestEntity == null) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_INVALID_PARAM_ERROR);
        }
        if (!Objects.equals(RecycleRequestEntity.RES_STATUS_CONFIRM, recycleRequestEntity.getResStatus())) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        return Result.ResultBuilder.success(recycleRequestService.completeRecycleRequest(id));
    }


    private boolean checkParam(RecycleRequestVo recycleRequestVo) {
        if (recycleRequestVo == null) {
            logger.info("recycleRequestVo is null");
            return false;
        }
        if (recycleRequestVo.getResType() == null || !RecycleRequestEntity.RES_TYPES.contains(recycleRequestVo.getResType())) {
            logger.info("resType is null or invalid");
            return false;
        }
        if (recycleRequestVo.getResAmount() == null || !RecycleRequestEntity.RES_AMOUNTS.contains(recycleRequestVo.getResAmount())) {
            logger.info("resAmount is null or invalid");
            return false;
        }
        if (recycleRequestVo.getAddr() == null || recycleRequestVo.getAddr().getComplexId() == null || StringUtils.isBlank(recycleRequestVo.getAddr().getDoorInfo())) {
            logger.info("location info is null or empty");
            return false;
        }
        if (StringUtils.isBlank(recycleRequestVo.getMobilePhone())) {
            logger.info("mobilePhone is null or empty");
            return false;
        }
        return true;
    }

    /**
     * 电话号码脱敏处理
     *
     * @param mobilePhone
     * @return
     */
    private static String getHiddenMobilePhone(String mobilePhone) {
        if (StringUtils.isEmpty(mobilePhone)) {
            return "";
        }
        if (mobilePhone.length() > 5) {
            return mobilePhone.substring(0, 3) + "******" + mobilePhone.substring(mobilePhone.length() - 2);
        }
        return mobilePhone;
    }

    private List<RecycleRequestVo> buildRecycleRequestVoList(ListRecycleRequestBo listRecycleRequestBo, Integer page, Integer size, boolean needHidden) {
        int pageVal = (page == null || page <= 0) ? 1 : page.intValue();
        int pageSize = (size == null || size <= 0) ? 10 : size.intValue();
        int startPage = (pageVal - 1) * pageSize;
        listRecycleRequestBo.setStartPage(startPage);
        listRecycleRequestBo.setSize(pageSize);
        List<RecycleRequestEntity> requestEntityList = recycleRequestService.list(listRecycleRequestBo);
        if (CollectionUtils.isEmpty(requestEntityList)) {
            return Collections.emptyList();
        }
        List<Long> complexIds = requestEntityList.stream().filter(e -> e.getComplexId() != null).map(RecycleRequestEntity::getComplexId).collect(Collectors.toList());
        List<ComplexEntity> complexEntityList = complexService.findByIds(complexIds);
        Map<Long, ComplexEntity> complexEntityMap = complexEntityList.stream().collect(Collectors.toMap(ComplexEntity::getId, e -> e));
        return requestEntityList.stream().map(e -> buildRecycleRequestVo(e, complexEntityMap, needHidden)).collect(Collectors.toList());
    }


    private RecycleRequestEntity convertVo2Entity(RecycleRequestVo recycleRequestVo) {
        RecycleRequestEntity recycleRequestEntity = new RecycleRequestEntity();
        recycleRequestEntity.setResType(recycleRequestVo.getResType());
        recycleRequestEntity.setResAmount(recycleRequestVo.getResAmount());
        recycleRequestEntity.setResImages(recycleRequestVo.getResImages());
        recycleRequestEntity.setTakeGarbageFlag(recycleRequestVo.isTakeGarbageFlag());
        recycleRequestEntity.setFreeTakeFlag(recycleRequestVo.isFreeTakeFlag());
        recycleRequestEntity.setResNote(recycleRequestVo.getResNote());
        if (StringUtils.isNotBlank(recycleRequestVo.getDoorServStartTime()) && StringUtils.isNotBlank(recycleRequestVo.getDoorServEndTime())) {
            recycleRequestEntity.setDoorServStartTime(DateUtil.getSecondDate(recycleRequestVo.getDoorServStartTime()));
            recycleRequestEntity.setDoorServEndTime(DateUtil.getSecondDate(recycleRequestVo.getDoorServEndTime()));
        }
        recycleRequestEntity.setMobilePhone(recycleRequestVo.getMobilePhone());
        recycleRequestEntity.setComplexId(recycleRequestVo.getAddr().getComplexId());
        recycleRequestEntity.setDoorInfo(recycleRequestVo.getAddr().getDoorInfo());
        return recycleRequestEntity;
    }

    private RecycleRequestVo buildRecycleRequestVo(RecycleRequestEntity e, Map<Long, ComplexEntity> complexEntityMap, boolean needHidden) {
        RecycleRequestVo vo = new RecycleRequestVo();
        vo.setId(e.getId());
        vo.setCreateUserId(e.getCreateUserId());
        vo.setResType(e.getResType());
        vo.setResStatus(e.getResStatus());
        vo.setResAmount(e.getResAmount());
        vo.setResImages(e.getResImages());
        vo.setTakeGarbageFlag(e.isTakeGarbageFlag());
        vo.setFreeTakeFlag(e.isFreeTakeFlag());
        vo.setResNote(e.getResNote());
        if (e.getDoorServStartTime() != null && e.getDoorServEndTime() != null) {
            vo.setDoorServStartTime(DateUtil.getSecondStr(e.getDoorServStartTime()));
            vo.setDoorServEndTime(DateUtil.getSecondStr(e.getDoorServEndTime()));
        }
        if (needHidden) {
            vo.setMobilePhone(getHiddenMobilePhone(e.getMobilePhone()));
        } else {
            vo.setMobilePhone(e.getMobilePhone());
        }
        vo.setAddr(buildAddr(e, complexEntityMap, needHidden));
        if (e.getPublishTime() != null) {
            vo.setPublishTime(DateUtil.getSecondStr(e.getPublishTime()));
        }
        if (e.getConfirmRecycleTime() != null) {
            vo.setConfirmRecycleTime(DateUtil.getSecondStr(e.getConfirmRecycleTime()));
        }
        return vo;
    }

    private Addr buildAddr(RecycleRequestEntity e, Map<Long, ComplexEntity> complexEntityMap, boolean needHidden) {
        Addr addr = new Addr();
        if (e.getComplexId() != null) {
            ComplexEntity complexEntity;
            if (MapUtils.isNotEmpty(complexEntityMap)) {
                complexEntity = complexEntityMap.get(e.getComplexId());
            } else {
                complexEntity = complexService.find(e.getComplexId());
            }
            if (complexEntity != null) {
                addr.setComplexVo(BeanUtil.copy(complexEntity, ComplexVo.class));
            }
            addr.setDoorInfo(e.getDoorInfo());
        }
        if (needHidden) {
            // 隐藏详细地址
            addr.setDoorInfo("******");
        }
        return addr;
    }
}
