package cn.zhibanxia.zbxserver.controller.rest;

import cn.zhibanxia.zbxserver.bo.SearchUserBo;
import cn.zhibanxia.zbxserver.constant.ErrorCode;
import cn.zhibanxia.zbxserver.controller.param.*;
import cn.zhibanxia.zbxserver.entity.ComplexEntity;
import cn.zhibanxia.zbxserver.entity.UserAddressEntity;
import cn.zhibanxia.zbxserver.entity.UserEntity;
import cn.zhibanxia.zbxserver.exception.BizException;
import cn.zhibanxia.zbxserver.filter.RequestLocal;
import cn.zhibanxia.zbxserver.service.ComplexService;
import cn.zhibanxia.zbxserver.service.UserAddrService;
import cn.zhibanxia.zbxserver.service.UserService;
import cn.zhibanxia.zbxserver.util.BeanUtil;
import cn.zhibanxia.zbxserver.util.LoggerUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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

    @Autowired
    private ComplexService complexService;

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
                userService.addMobileAndVerify(RequestLocal.get().getYezhuUid(), addUserDetailReq.getMobilePhone(), null, null, null);
                UserAddressEntity userAddressEntity = new UserAddressEntity();
                userAddressEntity.setUserId(RequestLocal.get().getYezhuUid());
                userAddressEntity.setBizType(UserAddressEntity.BIZ_TYPE_YEZHU);
                userAddressEntity.setComplexId(addUserDetailReq.getDefaultAddr().getComplexId());
                userAddressEntity.setDoorInfo(addUserDetailReq.getDefaultAddr().getDoorInfo());
                return Result.ResultBuilder.success(userAddrService.addOrUpdateOnlyAddr(userAddressEntity));
            } else {
                // 未审核通过
                if (!Objects.equals(RequestLocal.get().getHuishouUserEntity().getUserStatus(), UserEntity.USER_STATUS_NORMAL)) {
                    userService.addMobileAndVerify(RequestLocal.get().getHuishouUid(), addUserDetailReq.getMobilePhone(), addUserDetailReq.getVerifyLogo(), addUserDetailReq.getWxNotifyFlag(), addUserDetailReq.getVoiceNotifyFlag());
                    modifyHuishouAddrInfo(addUserDetailReq);
                    // 最终修改回收人员状态为审核中
                    return Result.ResultBuilder.success(userService.updateUserStatus(RequestLocal.get().getHuishouUid(), UserEntity.USER_STATUS_PERMIT_PROCESS));
                } else { // 资料已经审核通过，只能修改关注小区、通知配置
                    // 可以修改手机号、语音和微信通知配置，认证头像不能修改
                    userService.addMobileAndVerify(RequestLocal.get().getHuishouUid(), addUserDetailReq.getMobilePhone(), null, addUserDetailReq.getWxNotifyFlag(), addUserDetailReq.getVoiceNotifyFlag());
                    modifyHuishouAddrInfo(addUserDetailReq);
                    return Result.ResultBuilder.success(true);
                }
            }
        } catch (BizException e) {
            logger.warn("", e);
            return Result.ResultBuilder.fail(e.getErrorCode());
        } catch (Exception e) {
            logger.warn("", e);
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNKONWN_ERROR);
        }
    }

    private void modifyHuishouAddrInfo(AddUserDetailReq addUserDetailReq) throws BizException {
        UserAddressEntity userAddressEntity = new UserAddressEntity();
        userAddressEntity.setUserId(RequestLocal.get().getHuishouUid());
        userAddressEntity.setBizType(UserAddressEntity.BIZ_TYPE_HUISHOU);
        userAddressEntity.setComplexId(addUserDetailReq.getDefaultAddr().getComplexId());
        userAddressEntity.setDoorInfo(addUserDetailReq.getDefaultAddr().getDoorInfo());
        userAddrService.addOrUpdateOnlyAddr(userAddressEntity);

        List<UserAddressEntity> focusAddrs = addUserDetailReq.getFocusAddrList().stream().map(e -> {
            UserAddressEntity temp = new UserAddressEntity();
            temp.setId(e.getAddrId());
            temp.setUserId(RequestLocal.get().getHuishouUid());
            temp.setBizType(UserAddressEntity.BIZ_TYPE_HUISHOU_FOCUS);
            temp.setComplexId(e.getComplexId());
            temp.setDoorInfo(e.getDoorInfo());
            return temp;
        }).collect(Collectors.toList());
        userAddrService.batchAddAddr(RequestLocal.get().getHuishouUid(), focusAddrs);
    }

    @GetMapping("getUserType")
    public Result<UserIdentifyRsp> getUserType() {
        if (!(RequestLocal.get().isYezhu() || RequestLocal.get().isHuishou() || RequestLocal.get().isAdmin())) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        UserIdentifyRsp userIdentifyRsp = new UserIdentifyRsp();
        userIdentifyRsp.setUserType(RequestLocal.get().getUserType());
        if (RequestLocal.get().isHuishou()) {
            userIdentifyRsp.setUserStatus(RequestLocal.get().getHuishouUserEntity().getUserStatus());
        } else if (RequestLocal.get().isYezhu()) {
            userIdentifyRsp.setUserStatus(RequestLocal.get().getYezhuUserEntity().getUserStatus());
        } else if (RequestLocal.get().isAdmin()) {
            userIdentifyRsp.setUserStatus(RequestLocal.get().getAdminUserEntity().getUserStatus());
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
        if (userAddressEntity.getComplexId() != null) {
            addr.setComplexId(userAddressEntity.getComplexId());
            addr.setComplexVo(BeanUtil.copy(complexService.find(userAddressEntity.getComplexId()), ComplexVo.class));
            addr.setDoorInfo(userAddressEntity.getDoorInfo());
        }

        yezhuUserInfoRsp.setDefaultAddr(addr);
        return yezhuUserInfoRsp;
    }


    /**
     * 获取回收人员信息，可用于审核过程中状态查询、审核驳回信息展示
     *
     * @return
     */
    @GetMapping("getHuishouUserInfo")
    public Result<HuishouUserInfoRsp> getHuishouUserInfo(@RequestParam(value = "id", required = false) Long id) {
        if (id != null) {
            if (!RequestLocal.get().isAdmin()) {
                return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
            }
            return getHuishouUserInfo4Admin(id);
        }
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
    private Result<HuishouUserInfoRsp> getHuishouUserInfo4Admin(Long id) {
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
        huishouUserInfoRsp.setVoiceNotifyFlag(userEntity.getVoiceNotifyFlag());
        huishouUserInfoRsp.setWxNotifyFlag(userEntity.getWxNotifyFlag());

        UserAddressEntity userAddressEntity = userAddrService.findOnlyAddr(userEntity.getId(), UserAddressEntity.BIZ_TYPE_HUISHOU);
        if (userAddressEntity == null) {
            return huishouUserInfoRsp;
        }
        Addr addr = new Addr();
        addr.setAddrId(userAddressEntity.getId());
        if (userAddressEntity.getComplexId() != null) {
            addr.setComplexId(userAddressEntity.getComplexId());
            addr.setComplexVo(BeanUtil.copy(complexService.find(userAddressEntity.getComplexId()), ComplexVo.class));
            addr.setDoorInfo(userAddressEntity.getDoorInfo());
        }

        huishouUserInfoRsp.setDefaultAddr(addr);
        List<UserAddressEntity> focusAddrs = userAddrService.findFocusAddrs(userEntity.getId());
        if (CollectionUtils.isEmpty(focusAddrs)) {
            Result.ResultBuilder.success(huishouUserInfoRsp);
        }

        List<Long> complexIds = focusAddrs.stream().filter(e -> e.getComplexId() != null).map(UserAddressEntity::getComplexId).collect(Collectors.toList());
        Map<Long, ComplexEntity> complexEntityMap = null;
        if (CollectionUtils.isNotEmpty(complexIds)) {
            List<ComplexEntity> complexEntityList = complexService.findByIds(complexIds);
            if (CollectionUtils.isNotEmpty(complexEntityList)) {
                complexEntityMap = complexEntityList.stream().collect(Collectors.toMap(ComplexEntity::getId, c -> c));
            }
        }
        final Map<Long, ComplexEntity> fComplexEntityMap = complexEntityMap;
        List<Addr> addrs = focusAddrs.stream().map(e -> {
            Addr temp = new Addr();
            temp.setAddrId(e.getId());
            if (MapUtils.isNotEmpty(fComplexEntityMap) && e.getComplexId() != null) {
                temp.setComplexId(e.getComplexId());
                temp.setComplexVo(BeanUtil.copy(fComplexEntityMap.get(e.getComplexId()), ComplexVo.class));
                temp.setDoorInfo(e.getDoorInfo());
            }
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
    public Result<Boolean> verifyHuishou(@RequestBody VerifyHuishouVo verifyHuishouVo) {
        if (!RequestLocal.get().isAdmin()) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        try {
            UserEntity userEntity = userService.findById(verifyHuishouVo.getId());
            if (userEntity == null || !Objects.equals(userEntity.getUserType(), UserEntity.USER_TYPE_HUISHOU) || !Objects.equals(UserEntity.USER_STATUS_PERMIT_PROCESS, userEntity.getUserStatus())) {
                logger.warn("user({id}) not exist, or is not huishou, or status is not in permit process", verifyHuishouVo.getId());
                return Result.ResultBuilder.fail(ErrorCode.CODE_INVALID_PARAM_ERROR);
            }
            return Result.ResultBuilder.success(userService.verifyHuishou(verifyHuishouVo.getId(), verifyHuishouVo.getVerifyResult(), verifyHuishouVo.getVerifyRemark()));
        } catch (Exception e) {
            logger.warn("", e);
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNKONWN_ERROR);
        } finally {
            adminAccessLogger.info("uid={}|it={}|param={}", RequestLocal.get().getAdminUid(), "verifyHuishou", "id=" + verifyHuishouVo.getId() + ", verifyResult=" + verifyHuishouVo.getVerifyResult());
        }
    }

    /**
     * 修改用户状态，可用于禁用/恢复账号
     *
     * @return
     */
    @PostMapping("modifyUserStatus")
    public Result<Boolean> modifyUserStatus(@RequestBody ModifyUserStatusReq modifyUserStatusReq) {
        if (!RequestLocal.get().isAdmin()) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        Long id = modifyUserStatusReq.getId();
        Integer status = modifyUserStatusReq.getStatus();
        String remark = modifyUserStatusReq.getRemark();
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

    /**
     * 删除用户账户，硬删除
     *
     * @return
     */
    @PostMapping("deleteUser")
    public Result<Boolean> deleteUser(@RequestBody DeleteUserReq deleteUserReq) {
        if (!RequestLocal.get().isAdmin()) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        Long id = deleteUserReq.getId();
        String remark = deleteUserReq.getRemark();
        try {
            UserEntity userEntity = userService.findById(id);
            if (userEntity == null) {
                logger.warn("user({id}) not exist, or status is not in permit process", id);
                return Result.ResultBuilder.fail(ErrorCode.CODE_INVALID_PARAM_ERROR);
            }
            return Result.ResultBuilder.success(userService.delete(id));
        } catch (Exception e) {
            logger.warn("", e);
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNKONWN_ERROR);
        } finally {
            adminAccessLogger.info("uid={}|it={}|param={}", RequestLocal.get().getAdminUid(), "deleteUser", "id=" + id + ", remark=" + remark);
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


    @PostMapping("searchUser")
    public Result<ListRsp<UserDetail4Admin>> searchUser(@RequestBody SearchUserReq searchUserReq) {
        if (!RequestLocal.get().isAdmin()) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        Integer page = searchUserReq.getPage();
        Integer size = searchUserReq.getSize();
        int pageVal = (page == null || page <= 0) ? 1 : page.intValue();
        int pageSize = (size == null || size <= 0) ? 10 : size.intValue();
        int startPage = (pageVal - 1) * pageSize;
        int endPage = pageVal * pageSize;

        SearchUserBo searchUserBo = new SearchUserBo();
        searchUserBo.setStartPage(startPage);
        searchUserBo.setEndPage(endPage);
        searchUserBo.setUserType(getParamUserType(searchUserReq.getUserType()));
        searchUserBo.setUserStatus(getParamUserStatus(searchUserReq.getUserStatus()));
        searchUserBo.setSearchType(getParamSearchType(searchUserReq.getSearchType()));
        try {
            List<UserEntity> entityList = userService.searchUser(searchUserBo);
            int count = userService.countSearchUser(searchUserBo);
            ListRsp<UserDetail4Admin> listRsp = new ListRsp<>();
            listRsp.setTotalCount(count);
            listRsp.setList(BeanUtil.copyList(entityList, UserDetail4Admin.class));
            return Result.ResultBuilder.success(listRsp);
        } catch (Exception e) {
            logger.warn("", e);
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNKONWN_ERROR);
        } finally {
            adminAccessLogger.info("uid={}|it={}|param={}", RequestLocal.get().getAdminUid(), "searchUser", searchUserReq);
        }
    }

    private int getParamUserType(Integer userType) {
        if (userType == null || userType < 1 || userType > 2) {
            return UserEntity.USER_TYPE_HUISHOU;
        }
        return userType;
    }

    private int getParamUserStatus(Integer userStatus) {
        if (userStatus == null || userStatus < 1 || userStatus > 6) {
            return UserEntity.USER_STATUS_NORMAL;
        }
        return userStatus;
    }

    private int getParamSearchType(Integer searchType) {
        if (searchType == null || searchType < 1 || searchType > 2) {
            return 1;
        }
        return searchType;
    }

}
