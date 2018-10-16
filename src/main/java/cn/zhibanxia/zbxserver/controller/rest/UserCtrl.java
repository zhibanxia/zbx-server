package cn.zhibanxia.zbxserver.controller.rest;

import cn.zhibanxia.zbxserver.constant.ErrorCode;
import cn.zhibanxia.zbxserver.controller.param.*;
import cn.zhibanxia.zbxserver.entity.UserAddressEntity;
import cn.zhibanxia.zbxserver.entity.UserEntity;
import cn.zhibanxia.zbxserver.exception.BizException;
import cn.zhibanxia.zbxserver.filter.RequestLocal;
import cn.zhibanxia.zbxserver.service.UserAddrService;
import cn.zhibanxia.zbxserver.service.UserService;
import cn.zhibanxia.zbxserver.util.BeanUtil;
import cn.zhibanxia.zbxserver.util.LoggerUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by zzy on  2018/10/02 22:13
 */
@RestController
@RequestMapping("/rest/user")
public class UserCtrl {

    private static Logger logger = LoggerFactory.getLogger(UserCtrl.class);

    private static Logger adminAccessLogger = LoggerUtil.getAdminAccessLogger();

    @Autowired
    private UserService userService;
    @Autowired
    private UserAddrService userAddrService;

    /**
     * 增加个人信息详情，可用于用户首次登陆补充信息，含回收人员提交审核请求
     *
     * @return
     */
    @PostMapping("addUserDetail")
    public Result<Boolean> addUserDetail(@RequestBody AddUserDetailReq addUserDetailReq) {
        if (!RequestLocal.get().isYezhu() && !RequestLocal.get().isHuishou()) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        try {
            if (RequestLocal.get().isYezhu()) {
                userService.addMobileAndVerify(RequestLocal.get().getYezhuUid(), addUserDetailReq.getMobilePhone(), null);
                UserAddressEntity userAddressEntity = new UserAddressEntity();
                userAddressEntity.setUserId(RequestLocal.get().getYezhuUid());
                userAddressEntity.setBizType(UserAddressEntity.BIZ_TYPE_YEZHU);
                userAddressEntity.setProvinceId(addUserDetailReq.getDefaultAddr().getProvinceId());
                userAddressEntity.setCityId(addUserDetailReq.getDefaultAddr().getCityId());
                userAddressEntity.setAreaId(addUserDetailReq.getDefaultAddr().getAreaId());
                userAddressEntity.setSubdistrictId(addUserDetailReq.getDefaultAddr().getSubdistrictId());
                userAddressEntity.setAddrDetail(addUserDetailReq.getDefaultAddr().getAddrDetail());
                return Result.ResultBuilder.success(userAddrService.addOrUpdateOnlyAddr(userAddressEntity));
            } else {
                if (Objects.equals(RequestLocal.get().getHuishouUserEntity().getUserStatus(), UserEntity.USER_STATUS_NORMAL)) {
                    // 资料已经审核通过，不能修改
                    return Result.ResultBuilder.fail(ErrorCode.CODE_USER_CANNOT_MODIFY_ADDR_ERROR);
                }
                userService.addMobileAndVerify(RequestLocal.get().getHuishouUid(), addUserDetailReq.getMobilePhone(), addUserDetailReq.getVerifyLogo());
                UserAddressEntity userAddressEntity = new UserAddressEntity();
                userAddressEntity.setUserId(RequestLocal.get().getHuishouUid());
                userAddressEntity.setBizType(UserAddressEntity.BIZ_TYPE_HUISHOU);
                userAddressEntity.setProvinceId(addUserDetailReq.getDefaultAddr().getProvinceId());
                userAddressEntity.setCityId(addUserDetailReq.getDefaultAddr().getCityId());
                userAddressEntity.setAreaId(addUserDetailReq.getDefaultAddr().getAreaId());
                userAddressEntity.setSubdistrictId(addUserDetailReq.getDefaultAddr().getSubdistrictId());
                userAddressEntity.setAddrDetail(addUserDetailReq.getDefaultAddr().getAddrDetail());
                userAddrService.addOrUpdateOnlyAddr(userAddressEntity);

                List<UserAddressEntity> focusAddrs = addUserDetailReq.getFocusAddrList().stream().map(e -> {
                    UserAddressEntity temp = new UserAddressEntity();
                    temp.setId(e.getAddrId());
                    temp.setUserId(RequestLocal.get().getHuishouUid());
                    temp.setBizType(UserAddressEntity.BIZ_TYPE_HUISHOU_FOCUS);
                    temp.setProvinceId(e.getProvinceId());
                    temp.setCityId(e.getCityId());
                    temp.setAreaId(e.getAreaId());
                    temp.setSubdistrictId(e.getSubdistrictId());
                    temp.setAddrDetail(e.getAddrDetail());
                    return temp;
                }).collect(Collectors.toList());
                return Result.ResultBuilder.success(userAddrService.batchAddAddr(RequestLocal.get().getHuishouUid(), focusAddrs));
            }
        } catch (BizException e) {
            logger.warn("", e);
            return Result.ResultBuilder.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.warn("", e);
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNKONWN_ERROR);
        }
    }

    @GetMapping("getUserType")
    public Result<UserIdentifyRsp> getUserType() {
        if (!(RequestLocal.get().isYezhu() || RequestLocal.get().isHuishou())) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        UserIdentifyRsp userIdentifyRsp = new UserIdentifyRsp();
        userIdentifyRsp.setUserType(RequestLocal.get().getUserType());
        if (RequestLocal.get().isHuishou()) {
            userIdentifyRsp.setUserStatus(RequestLocal.get().getHuishouUserEntity().getUserStatus());
        } else {
            userIdentifyRsp.setUserStatus(RequestLocal.get().getYezhuUserEntity().getUserStatus());
        }
        return Result.ResultBuilder.success(userIdentifyRsp);
    }


    /**
     * 业主个人信息展示，可用于发布回收请求时查询个人手机号码地址等
     *
     * @return
     */
    @GetMapping("getYezhuUserInfo")
    public Result<YezhuUserInfoRsp> getYezhuUserInfo() {
        if (!RequestLocal.get().isYezhu()) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        UserEntity userEntity = RequestLocal.get().getYezhuUserEntity();
        return Result.ResultBuilder.success(buildYezhuUserInfoRsp(userEntity));
    }

    /**
     * 获取回收人员信息，可用于审核过程中状态查询、审核驳回信息展示
     *
     * @return
     */
    @GetMapping("getYezhuUserInfo4Admin")
    public Result<YezhuUserInfoRsp> getYezhuUserInfo4Admin(@RequestParam("id") Long id) {
        if (!RequestLocal.get().isAdmin()) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        try {
            UserEntity userEntity = userService.findById(id);
            if (userEntity == null || !Objects.equals(userEntity.getUserType(), UserEntity.USER_TYPE_YEZHU)) {
                logger.warn("user({id}) not exist, or is not yezhu", id);
                return Result.ResultBuilder.fail(ErrorCode.CODE_INVALID_PARAM_ERROR);
            }
            return Result.ResultBuilder.success(buildYezhuUserInfoRsp(userEntity));
        } catch (Exception e) {
            logger.warn("", e);
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNKONWN_ERROR);
        } finally {
            adminAccessLogger.info("uid={}|it={}|param={}", RequestLocal.get().getAdminUid(), "getYezhuUserInfo4Admin", "id=" + id);
        }
    }


    private YezhuUserInfoRsp buildYezhuUserInfoRsp(UserEntity userEntity) {
        YezhuUserInfoRsp yezhuUserInfoRsp = new YezhuUserInfoRsp();
        yezhuUserInfoRsp.setUid(userEntity.getId());
        yezhuUserInfoRsp.setWxNickName(userEntity.getWxNickName());
        yezhuUserInfoRsp.setWxLogo(userEntity.getWxLogo());
        yezhuUserInfoRsp.setMobilePhone(userEntity.getMobilePhone());

        UserAddressEntity userAddressEntity = userAddrService.findOnlyAddr(userEntity.getId(), UserAddressEntity.BIZ_TYPE_YEZHU);
        if (userAddressEntity == null) {
            return yezhuUserInfoRsp;
        }
        Addr addr = new Addr();
        addr.setAddrId(userAddressEntity.getId());
        addr.setProvinceId(userAddressEntity.getProvinceId());
        addr.setCityId(userAddressEntity.getCityId());
        addr.setAreaId(userAddressEntity.getAreaId());
        addr.setSubdistrictId(userAddressEntity.getSubdistrictId());
        addr.setAddrDetail(userAddressEntity.getAddrDetail());
        yezhuUserInfoRsp.setDefaultAddr(addr);
        return yezhuUserInfoRsp;
    }


    /**
     * 获取回收人员信息，可用于审核过程中状态查询、审核驳回信息展示
     *
     * @return
     */
    @GetMapping("getHuishouUserInfo")
    public Result<HuishouUserInfoRsp> getHuishouUserInfo() {
        if (!RequestLocal.get().isHuishou()) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        try {
            UserEntity userEntity = RequestLocal.get().getHuishouUserEntity();
            return Result.ResultBuilder.success(buildHuishouUserInfoRsp(userEntity));
        } catch (Exception e) {
            logger.warn("", e);
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNKONWN_ERROR);
        }
    }

    /**
     * 获取回收人员信息，可用于审核过程中状态查询、审核驳回信息展示
     *
     * @return
     */
    @GetMapping("getHuishouUserInfo4Admin")
    public Result<HuishouUserInfoRsp> getHuishouUserInfo4Admin(@RequestParam("id") Long id) {
        if (!RequestLocal.get().isAdmin()) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        try {
            UserEntity userEntity = userService.findById(id);
            if (userEntity == null || !Objects.equals(userEntity.getUserType(), UserEntity.USER_TYPE_HUISHOU)) {
                logger.warn("user({id}) not exist, or is not huishou", id);
                return Result.ResultBuilder.fail(ErrorCode.CODE_INVALID_PARAM_ERROR);
            }
            return Result.ResultBuilder.success(buildHuishouUserInfoRsp(userEntity));
        } catch (Exception e) {
            logger.warn("", e);
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNKONWN_ERROR);
        } finally {
            adminAccessLogger.info("uid={}|it={}|param={}", RequestLocal.get().getAdminUid(), "getHuishouUserInfo4Admin", "id=" + id);
        }
    }

    private HuishouUserInfoRsp buildHuishouUserInfoRsp(UserEntity userEntity) {
        HuishouUserInfoRsp huishouUserInfoRsp = new HuishouUserInfoRsp();
        huishouUserInfoRsp.setUid(userEntity.getId());
        huishouUserInfoRsp.setWxNickName(userEntity.getWxNickName());
        huishouUserInfoRsp.setWxLogo(userEntity.getWxLogo());
        huishouUserInfoRsp.setMobilePhone(userEntity.getMobilePhone());
        huishouUserInfoRsp.setVerifyLogo(userEntity.getVerifyLogo());
        huishouUserInfoRsp.setStatus(userEntity.getUserStatus());

        UserAddressEntity userAddressEntity = userAddrService.findOnlyAddr(userEntity.getId(), UserAddressEntity.BIZ_TYPE_HUISHOU);
        if (userAddressEntity == null) {
            return huishouUserInfoRsp;
        }
        Addr addr = new Addr();
        addr.setAddrId(userAddressEntity.getId());
        addr.setProvinceId(userAddressEntity.getProvinceId());
        addr.setCityId(userAddressEntity.getCityId());
        addr.setAreaId(userAddressEntity.getAreaId());
        addr.setSubdistrictId(userAddressEntity.getSubdistrictId());
        addr.setAddrDetail(userAddressEntity.getAddrDetail());
        huishouUserInfoRsp.setDefaultAddr(addr);
        List<UserAddressEntity> focusAddrs = userAddrService.findFocusAddrs(userEntity.getId());
        if (CollectionUtils.isEmpty(focusAddrs)) {
            Result.ResultBuilder.success(huishouUserInfoRsp);
        }
        List<Addr> addrs = focusAddrs.stream().map(e -> {
            Addr temp = new Addr();
            temp.setAddrId(e.getId());
            temp.setProvinceId(e.getProvinceId());
            temp.setCityId(e.getCityId());
            temp.setAreaId(e.getAreaId());
            temp.setSubdistrictId(e.getSubdistrictId());
            temp.setAddrDetail(e.getAddrDetail());
            return temp;
        }).collect(Collectors.toList());
        huishouUserInfoRsp.setFocusAddrList(addrs);
        return huishouUserInfoRsp;
    }


    /**
     * 获取回收人员信息，可用于审核过程中状态查询、审核驳回信息展示
     *
     * @return
     */
    @PostMapping("verifyHuishou")
    public Result<Boolean> verifyHuishou(@RequestParam("id") Long id, @RequestParam("verifyResult") boolean verifyResult, @RequestParam(value = "verifyRemark", required = false) String verifyRemark) {
        if (!RequestLocal.get().isAdmin()) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        try {
            UserEntity userEntity = userService.findById(id);
            if (userEntity == null || !Objects.equals(userEntity.getUserType(), UserEntity.USER_TYPE_HUISHOU) || !Objects.equals(UserEntity.USER_STATUS_PERMIT_PROCESS, userEntity.getUserStatus())) {
                logger.warn("user({id}) not exist, or is not huishou, or status is not in permit process", id);
                return Result.ResultBuilder.fail(ErrorCode.CODE_INVALID_PARAM_ERROR);
            }
            return Result.ResultBuilder.success(userService.verifyHuishou(id, verifyResult, verifyRemark));
        } catch (Exception e) {
            logger.warn("", e);
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNKONWN_ERROR);
        } finally {
            adminAccessLogger.info("uid={}|it={}|param={}", RequestLocal.get().getAdminUid(), "verifyHuishou", "id=" + id + ", verifyResult=" + verifyResult);
        }
    }

    /**
     * 修改用户状态，可用于禁用/恢复账号
     *
     * @return
     */
    @GetMapping("modifyUserStatus")
    public Result<Boolean> modifyUserStatus(@RequestParam("id") Long id, @RequestParam("status") int status, @RequestParam(value = "remark", required = false) String remark) {
        if (!RequestLocal.get().isAdmin()) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        if (!UserEntity.STATUS_SET.contains(status)) {
            logger.warn("status() is invalid", status);
            return Result.ResultBuilder.fail(ErrorCode.CODE_INVALID_PARAM_ERROR);
        }
        try {
            UserEntity userEntity = userService.findById(id);
            if (userEntity == null) {
                logger.warn("user({id}) not exist, or status is not in permit process", id);
                return Result.ResultBuilder.fail(ErrorCode.CODE_INVALID_PARAM_ERROR);
            }
            return Result.ResultBuilder.success(userService.updateUserStatus(id, status));
        } catch (Exception e) {
            logger.warn("", e);
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNKONWN_ERROR);
        } finally {
            adminAccessLogger.info("uid={}|it={}|param={}", RequestLocal.get().getAdminUid(), "modifyUserStatus", "id=" + id + ", status=" + status + ", remark=" + remark);
        }
    }


    @GetMapping("getAllUser")
    public Result<ListRsp<UserDetail4Admin>> getAllUser(@RequestParam("page") Integer page,
                                                        @RequestParam(value = "size", required = false) Integer size) {
        if (!RequestLocal.get().isAdmin()) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        int pageVal = (page == null || page <= 0) ? 1 : page.intValue();
        int pageSize = (size == null || size <= 0) ? 10 : size.intValue();
        int startPage = (pageVal - 1) * pageSize;
        int endPage = pageVal * pageSize;
        try {
            List<UserEntity> entityList = userService.listAllUser(startPage, endPage);
            int count = userService.countAllUser();
            ListRsp<UserDetail4Admin> listRsp = new ListRsp<>();
            listRsp.setTotalCount(count);
            listRsp.setList(BeanUtil.copyList(entityList, UserDetail4Admin.class));
            return Result.ResultBuilder.success(listRsp);
        } catch (Exception e) {
            logger.warn("", e);
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNKONWN_ERROR);
        } finally {
            adminAccessLogger.info("uid={}|it={}|param={}", RequestLocal.get().getAdminUid(), "getAllUser", "page=" + page + ", size=" + size);
        }
    }

}
