package cn.zhibanxia.zbxserver.controller.rest;

import cn.zhibanxia.zbxserver.constant.ErrorCode;
import cn.zhibanxia.zbxserver.controller.param.Addr;
import cn.zhibanxia.zbxserver.controller.param.RecycleRequestVo;
import cn.zhibanxia.zbxserver.controller.param.Result;
import cn.zhibanxia.zbxserver.entity.RecycleRequestEntity;
import cn.zhibanxia.zbxserver.entity.UserEntity;
import cn.zhibanxia.zbxserver.filter.RequestLocal;
import cn.zhibanxia.zbxserver.service.RecycleRequestService;
import cn.zhibanxia.zbxserver.util.DateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by zzy on  2018/10/02 14:48
 */
@RestController("rest/recycle")
public class RecycleCtrl {
    private static Logger logger = LoggerFactory.getLogger(RecycleCtrl.class);
    @Autowired
    private RecycleRequestService recycleRequestService;

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
            RecycleRequestService.ListReq listReq = new RecycleRequestService.ListReq();
            listReq.setResStatus(RecycleRequestEntity.RES_STATUS_PUBLISH);
            listReq.setDeleted(false);
            return Result.ResultBuilder.success(buildRecycleRequestVoList(listReq, page, size, true));
        } else if (Objects.equals(2, bizType)) {
            RecycleRequestService.ListReq listReq = new RecycleRequestService.ListReq();
            listReq.setRecycleUserId(RequestLocal.get().getHuishouUid());
            listReq.setDeleted(false);
            return Result.ResultBuilder.success(buildRecycleRequestVoList(listReq, page, size, false));
        } else if (Objects.equals(3, bizType)) {
            RecycleRequestService.ListReq listReq = new RecycleRequestService.ListReq();
            listReq.setCreateUserId(RequestLocal.get().getYezhuUid());
            listReq.setDeleted(false);
            return Result.ResultBuilder.success(buildRecycleRequestVoList(listReq, page, size, false));
        } else {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
    }

    @GetMapping("detail")
    public Result<RecycleRequestVo> getRecyleRequestDetail(@RequestParam("id") Long id, @RequestParam("bizType") Integer bizType) {
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
        RecycleRequestEntity e = recycleRequestService.find(id);
        if (e == null) {
            return Result.ResultBuilder.success(null);
        }
        if (Objects.equals(1, bizType)) {
            return Result.ResultBuilder.success(buildRecycleRequestVo(e, true));
        } else if (Objects.equals(2, bizType)) {
            // 不允许查询非自己确认的回收请求
            if (!RequestLocal.get().getHuishouUid().equals(e.getRecycleUserId())) {
                return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
            }
            return Result.ResultBuilder.success(buildRecycleRequestVo(e, false));
        } else if (Objects.equals(3, bizType)) {
            // 不允许查询非自己创建的回收请求
            if (!RequestLocal.get().getYezhuUid().equals(e.getCreateUserId())) {
                return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
            }
            return Result.ResultBuilder.success(buildRecycleRequestVo(e, false));
        } else {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
    }

    @PostMapping("create")
    public Result<Void> create(RecycleRequestVo recycleRequestVo) {
        if (!RequestLocal.get().isYezhu()) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        if (!(checkParam(recycleRequestVo))) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_INVALID_PARAM_ERROR);
        }
        try {
            RecycleRequestEntity recycleRequestEntity = convertVo2Entity(recycleRequestVo);
            recycleRequestEntity.setCreateUserId(RequestLocal.get().getYezhuUid());
            recycleRequestService.create(recycleRequestEntity);
            return Result.ResultBuilder.success(null);
        } catch (Exception e) {
            logger.warn("", e);
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNKONWN_ERROR);
        }
    }

    @PostMapping("confirmRecycle")
    public Result<Void> confirmRecycle(@RequestParam("resId") Long resId) {
        // 如果不是回收人员、或者状态不是正常状态，则拒绝请求
        if (!RequestLocal.get().isHuishou() || !Objects.equals(RequestLocal.get().getHuishouUserEntity().getUserStatus(), UserEntity.USER_STATUS_NORMAL)) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        RecycleRequestEntity recycleRequestEntity = recycleRequestService.find(resId);
        if (recycleRequestEntity == null) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_INVALID_PARAM_ERROR);
        }
        if (!Objects.equals(RecycleRequestEntity.RES_STATUS_PUBLISH, recycleRequestEntity.getResStatus())) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_RECYCLE_HAS_HANDLED_ERROR);
        }
        boolean success = recycleRequestService.confirmRecycleRequest(resId, RequestLocal.get().getHuishouUid());
        if (!success) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_RECYCLE_HAS_HANDLED_ERROR);
        }
        return Result.ResultBuilder.success(null);
    }

    @PostMapping("delete")
    public Result<Void> delete(@RequestParam("resId") Long resId) {
        // 如果不是业主则拒绝请求
        if (!RequestLocal.get().isYezhu()) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        RecycleRequestEntity recycleRequestEntity = recycleRequestService.find(resId);
        if (recycleRequestEntity == null) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_INVALID_PARAM_ERROR);
        }
        if (!Objects.equals(RecycleRequestEntity.RES_STATUS_PUBLISH, recycleRequestEntity.getResStatus())) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_RECYCLE_HAS_HANDLED_ERROR);
        }
        recycleRequestService.delete(resId);
        return Result.ResultBuilder.success(null);
    }

    @PostMapping("update")
    public Result<Void> update(RecycleRequestVo recycleRequestVo) {
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
            recycleRequestService.update(convertVo2Entity(recycleRequestVo));
            return Result.ResultBuilder.success(null);
        } catch (Exception e) {
            logger.warn("", e);
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNKONWN_ERROR);
        }
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
        if (recycleRequestVo.getAddr() == null || StringUtils.isBlank(recycleRequestVo.getAddr().getProvinceId()) || StringUtils.isBlank(recycleRequestVo.getAddr().getCityId()) || StringUtils.isBlank(recycleRequestVo.getAddr().getAreaId()) ||
                StringUtils.isBlank(recycleRequestVo.getAddr().getSubdistrictId()) || StringUtils.isBlank(recycleRequestVo.getAddr().getAddrDetail())) {
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

    private List<RecycleRequestVo> buildRecycleRequestVoList(RecycleRequestService.ListReq listReq, Integer page, Integer size, boolean needHidden) {
        int pageVal = (page == null || page <= 0) ? 1 : page.intValue();
        int pageSize = (size == null || size <= 0) ? 10 : size.intValue();
        int startPage = pageVal * (pageSize - 1) + 1;
        int endPage = pageVal * pageSize;
        listReq.setStartPage(startPage);
        listReq.setEndPage(endPage);
        List<RecycleRequestEntity> requestEntityList = recycleRequestService.list(listReq);
        if (CollectionUtils.isEmpty(requestEntityList)) {
            Result.ResultBuilder.success(Collections.emptyList());
        }
        return requestEntityList.stream().map(e -> buildRecycleRequestVo(e, needHidden)).collect(Collectors.toList());
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
        recycleRequestEntity.setProvinceId(recycleRequestVo.getAddr().getProvinceId());
        recycleRequestEntity.setCityId(recycleRequestVo.getAddr().getCityId());
        recycleRequestEntity.setAreaId(recycleRequestVo.getAddr().getAreaId());
        recycleRequestEntity.setSubdistrictId(recycleRequestVo.getAddr().getSubdistrictId());
        recycleRequestEntity.setAddrDetail(recycleRequestVo.getAddr().getAddrDetail());
        recycleRequestEntity.setMobilePhone(recycleRequestVo.getMobilePhone());
        return recycleRequestEntity;
    }

    private static RecycleRequestVo buildRecycleRequestVo(RecycleRequestEntity e, boolean needHidden) {
        RecycleRequestVo vo = new RecycleRequestVo();
        vo.setId(e.getId());
        vo.setCreateUserId(e.getCreateUserId());
        vo.setResType(e.getResType());
        vo.setResAmount(e.getResAmount());
        vo.setResImages(e.getResImages());
        vo.setTakeGarbageFlag(e.isTakeGarbageFlag());
        vo.setFreeTakeFlag(e.isFreeTakeFlag());
        vo.setResNote(e.getResNote());
        if (e.getDoorServStartTime() != null && e.getDoorServEndTime() != null) {
            vo.setDoorServStartTime(DateUtil.getSecondStr(e.getDoorServStartTime()));
            vo.setDoorServEndTime(DateUtil.getSecondStr(e.getDoorServEndTime()));
        }
        Addr addr = new Addr();
        addr.setProvinceId(e.getProvinceId());
        addr.setCityId(e.getCityId());
        addr.setAreaId(e.getAreaId());
        addr.setSubdistrictId(e.getSubdistrictId());
        if (needHidden) {
            // 隐藏详细地址
            addr.setAddrDetail("******");
            vo.setMobilePhone(getHiddenMobilePhone(e.getMobilePhone()));
        } else {
            addr.setAddrDetail(e.getAddrDetail());
            vo.setMobilePhone(e.getMobilePhone());
        }
        vo.setAddr(addr);
        if (e.getPublishTime() != null) {
            vo.setPublishTime(DateUtil.getSecondStr(e.getPublishTime()));
        }
        if (e.getConfirmRecyleTime() != null) {
            vo.setConfirmRecycleTime(DateUtil.getSecondStr(e.getConfirmRecyleTime()));
        }
        return vo;
    }
}
