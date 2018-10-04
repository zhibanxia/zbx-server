package cn.zhibanxia.zbxserver.controller.rest;

import cn.zhibanxia.zbxserver.constant.ErrorCode;
import cn.zhibanxia.zbxserver.controller.param.AddUserDetailReq;
import cn.zhibanxia.zbxserver.controller.param.Addr;
import cn.zhibanxia.zbxserver.controller.param.HuishouUserInfoRsp;
import cn.zhibanxia.zbxserver.controller.param.YezhuUserInfoRsp;
import cn.zhibanxia.zbxserver.entity.UserAddressEntity;
import cn.zhibanxia.zbxserver.entity.UserEntity;
import cn.zhibanxia.zbxserver.filter.RequestLocal;
import cn.zhibanxia.zbxserver.service.UserAddrService;
import cn.zhibanxia.zbxserver.service.UserService;
import cn.zhibanxia.zbxserver.controller.param.Result;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by zzy on  2018/10/02 22:13
 */
@RestController("rest/user")
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
    public Result<Void> addUserDetail(AddUserDetailReq addUserDetailReq) {
        try {
            if (RequestLocal.get().isYezhu()) {
                userService.addMobileAndVerify(RequestLocal.get().getYezhuUid(), addUserDetailReq.getMobilePhone(), null);
                UserAddressEntity userAddressEntity = new UserAddressEntity();
                userAddressEntity.setUserId(RequestLocal.get().getYezhuUid());
                userAddressEntity.setBizType(UserAddressEntity.BIZ_TYPE_YEZHU);
                userAddressEntity.setProvinceId(addUserDetailReq.getDeafultAddr().getProvinceId());
                userAddressEntity.setCityId(addUserDetailReq.getDeafultAddr().getCityId());
                userAddressEntity.setAreaId(addUserDetailReq.getDeafultAddr().getAreaId());
                userAddressEntity.setSubdistrictId(addUserDetailReq.getDeafultAddr().getSubdistrictId());
                userAddressEntity.setAreaDetail(addUserDetailReq.getDeafultAddr().getAddrDetail());
                userAddrService.addOrUpdateOnlyAddr(userAddressEntity);
                return Result.ResultBuilder.success(null);
            } else if (RequestLocal.get().isHuishou()) {
                if (Objects.equals(RequestLocal.get().getHuishouUserEntity().getUserStatus(), UserEntity.USER_STATUS_NORMAL)) {
                    // 资料已经审核通过，不能修改
                    return Result.ResultBuilder.fail(ErrorCode.CODE_USER_CANNOT_MODIFY_ADDR_ERROR);
                }
                userService.addMobileAndVerify(RequestLocal.get().getHuishouUid(), addUserDetailReq.getMobilePhone(), addUserDetailReq.getVerifyLogo());
                UserAddressEntity userAddressEntity = new UserAddressEntity();
                userAddressEntity.setUserId(RequestLocal.get().getHuishouUid());
                userAddressEntity.setBizType(UserAddressEntity.BIZ_TYPE_HUISHOU);
                userAddressEntity.setProvinceId(addUserDetailReq.getDeafultAddr().getProvinceId());
                userAddressEntity.setCityId(addUserDetailReq.getDeafultAddr().getCityId());
                userAddressEntity.setAreaId(addUserDetailReq.getDeafultAddr().getAreaId());
                userAddressEntity.setSubdistrictId(addUserDetailReq.getDeafultAddr().getSubdistrictId());
                userAddressEntity.setAreaDetail(addUserDetailReq.getDeafultAddr().getAddrDetail());
                userAddrService.addOrUpdateOnlyAddr(userAddressEntity);

                List<UserAddressEntity> focusAddrs = addUserDetailReq.getFocusAddrList().stream().map(e -> {
                    UserAddressEntity temp = new UserAddressEntity();
                    temp.setUserId(RequestLocal.get().getHuishouUid());
                    temp.setBizType(UserAddressEntity.BIZ_TYPE_HUISHOU_FOCUS);
                    temp.setProvinceId(e.getProvinceId());
                    temp.setCityId(e.getCityId());
                    temp.setAreaId(e.getAreaId());
                    temp.setSubdistrictId(e.getSubdistrictId());
                    temp.setAreaDetail(e.getAddrDetail());
                    return temp;
                }).collect(Collectors.toList());

                userAddrService.batchAddAddr(focusAddrs);
                return Result.ResultBuilder.success(null);
            }
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        } catch (Exception e) {
            logger.warn("", e);
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNKONWN_ERROR);
        }
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
        addr.setAddrDetail(userAddressEntity.getAreaDetail());
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
        if (!RequestLocal.get().isYezhu()) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        UserEntity userEntity = RequestLocal.get().getYezhuUserEntity();
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
        addr.setAddrDetail(userAddressEntity.getAreaDetail());
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
            temp.setAddrDetail(e.getAreaDetail());
            return temp;
        }).collect(Collectors.toList());
        huishouUserInfoRsp.setFocusAddrList(addrs);
        return Result.ResultBuilder.success(huishouUserInfoRsp);
    }


}
