package cn.zhibanxia.zbxserver.controller.rest;

import cn.zhibanxia.zbxserver.constant.ErrorCode;
import cn.zhibanxia.zbxserver.controller.param.*;
import cn.zhibanxia.zbxserver.entity.UserAddressEntity;
import cn.zhibanxia.zbxserver.entity.UserEntity;
import cn.zhibanxia.zbxserver.exception.BizException;
import cn.zhibanxia.zbxserver.filter.RequestLocal;
import cn.zhibanxia.zbxserver.service.UserAddrService;
import cn.zhibanxia.zbxserver.service.UserService;
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
        YezhuUserInfoRsp yezhuUserInfoRsp = new YezhuUserInfoRsp();
        yezhuUserInfoRsp.setUid(userEntity.getId());
        yezhuUserInfoRsp.setWxNickName(userEntity.getWxNickName());
        yezhuUserInfoRsp.setWxLogo(userEntity.getWxLogo());
        yezhuUserInfoRsp.setMobilePhone(userEntity.getMobilePhone());

        UserAddressEntity userAddressEntity = userAddrService.findOnlyAddr(userEntity.getId(), UserAddressEntity.BIZ_TYPE_YEZHU);
        if (userAddressEntity == null) {
            return Result.ResultBuilder.success(yezhuUserInfoRsp);
        }
        Addr addr = new Addr();
        addr.setAddrId(userAddressEntity.getId());
        addr.setProvinceId(userAddressEntity.getProvinceId());
        addr.setCityId(userAddressEntity.getCityId());
        addr.setAreaId(userAddressEntity.getAreaId());
        addr.setSubdistrictId(userAddressEntity.getSubdistrictId());
        addr.setAddrDetail(userAddressEntity.getAddrDetail());
        yezhuUserInfoRsp.setDefaultAddr(addr);
        return Result.ResultBuilder.success(yezhuUserInfoRsp);
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
        UserEntity userEntity = RequestLocal.get().getHuishouUserEntity();
        HuishouUserInfoRsp huishouUserInfoRsp = new HuishouUserInfoRsp();
        huishouUserInfoRsp.setUid(userEntity.getId());
        huishouUserInfoRsp.setWxNickName(userEntity.getWxNickName());
        huishouUserInfoRsp.setWxLogo(userEntity.getWxLogo());
        huishouUserInfoRsp.setMobilePhone(userEntity.getMobilePhone());
        huishouUserInfoRsp.setVerifyLogo(userEntity.getVerifyLogo());
        huishouUserInfoRsp.setStatus(userEntity.getUserStatus());

        UserAddressEntity userAddressEntity = userAddrService.findOnlyAddr(userEntity.getId(), UserAddressEntity.BIZ_TYPE_HUISHOU);
        if (userAddressEntity == null) {
            return Result.ResultBuilder.success(huishouUserInfoRsp);
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
        return Result.ResultBuilder.success(huishouUserInfoRsp);
    }
}
