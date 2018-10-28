package cn.zhibanxia.zbxserver.controller;

import cn.zhibanxia.zbxserver.annotation.NotLoginCanAccess;
import cn.zhibanxia.zbxserver.bo.WxUserAuthBo;
import cn.zhibanxia.zbxserver.bo.WxUserInfoBo;
import cn.zhibanxia.zbxserver.config.WxPropConfig;
import cn.zhibanxia.zbxserver.config.ZbxConfig;
import cn.zhibanxia.zbxserver.constant.ErrorCode;
import cn.zhibanxia.zbxserver.controller.param.UserCookieVo;
import cn.zhibanxia.zbxserver.entity.UserEntity;
import cn.zhibanxia.zbxserver.exception.BizException;
import cn.zhibanxia.zbxserver.service.UserService;
import cn.zhibanxia.zbxserver.service.WxApiService;
import cn.zhibanxia.zbxserver.util.UserCookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Objects;

/**
 * Created by zzy on  2018/10/02 09:34
 */
@RestController
@RequestMapping("weixin")
@NotLoginCanAccess
public class WeixinIndexCtrl {

    private static Logger logger = LoggerFactory.getLogger(WeixinIndexCtrl.class);
    @Autowired
    private WxApiService wxApiService;
    @Autowired
    private UserService userService;
    @Autowired
    private WxPropConfig wxPropConfig;
    @Autowired
    private ZbxConfig zbxConfig;

    /**
     * 重定向到业主页
     *
     * @param code
     * @param type
     * @return
     */
    @GetMapping(value = "redirectIndex")
    public ModelAndView redirectYezhuIndex(@RequestParam("code") String code, @RequestParam("state") Integer type, HttpServletResponse response) {
        try {
            if (type == null || !(type.equals(UserEntity.USER_TYPE_YEZHU) || type.equals(UserEntity.USER_TYPE_HUISHOU) || type.equals(UserEntity.USER_TYPE_ADMIN))) {
                ModelAndView modelAndView = new ModelAndView("/error");
                modelAndView.addObject("code", ErrorCode.CODE_UNKONWN_ERROR.getCode());
                modelAndView.addObject("msg", "state参数不合法");
                return modelAndView;
            }
            WxUserAuthBo wxUserAuthBo = wxApiService.userAuth(code);
            if (type.equals(UserEntity.USER_TYPE_YEZHU)) {
                UserEntity userEntity = userService.findUserByOpenId(wxUserAuthBo.getOpenId(), UserEntity.USER_TYPE_YEZHU);
                Long id;
                if (userEntity == null) {
                    WxUserInfoBo wxUserInfoBo = wxApiService.getUserInfo(wxUserAuthBo.getAccessToken(), wxUserAuthBo.getOpenId());
                    userEntity = new UserEntity();
                    userEntity.setUserType(UserEntity.USER_TYPE_YEZHU);
                    userEntity.setWxLogo(wxUserInfoBo.getHeadimgurl());
                    userEntity.setWxOpenId(wxUserAuthBo.getOpenId());
                    userEntity.setWxNickName(wxUserInfoBo.getNickName());
                    userEntity.setUserStatus(UserEntity.USER_STATUS_NORMAL);
                    id = userService.createUser(userEntity);
                } else {
                    id = userEntity.getId();
                }
                UserCookieUtil.addCookie(response, UserEntity.USER_TYPE_YEZHU, id, zbxConfig.getEncryptKey());
                // 新增回收请求
                response.sendRedirect(zbxConfig.getZbxServiceDomain());
                return null;
            } else if (type.equals(UserEntity.USER_TYPE_HUISHOU)) {
                UserEntity userEntity = userService.findUserByOpenId(wxUserAuthBo.getOpenId(), UserEntity.USER_TYPE_HUISHOU);
                Long id;
                Integer userStatus;
                if (userEntity == null) {
                    WxUserInfoBo wxUserInfoBo = wxApiService.getUserInfo(wxUserAuthBo.getAccessToken(), wxUserAuthBo.getOpenId());
                    userEntity = new UserEntity();
                    userEntity.setUserType(UserEntity.USER_TYPE_HUISHOU);
                    userEntity.setWxLogo(wxUserInfoBo.getHeadimgurl());
                    userEntity.setWxOpenId(wxUserAuthBo.getOpenId());
                    userEntity.setWxNickName(wxUserInfoBo.getNickName());
                    userEntity.setUserStatus(UserEntity.USER_STATUS_NEED_ACTIVE);
                    id = userService.createUser(userEntity);
                    userStatus = UserEntity.USER_STATUS_NEED_ACTIVE;
                } else {
                    id = userEntity.getId();
                    userStatus = userEntity.getUserStatus();
                }
                UserCookieUtil.addCookie(response, UserEntity.USER_TYPE_HUISHOU, id, zbxConfig.getEncryptKey());
                return judgeHuishouUser(response, userStatus);
            } else if (type.equals(UserEntity.USER_TYPE_ADMIN)) {
                if (!zbxConfig.getAdminOpenIdSet().contains(wxUserAuthBo.getOpenId())) {
                    response.sendRedirect(zbxConfig.getZbxServiceDomain() + "/invalid.html");
                    return null;
                }
                UserEntity userEntity = userService.findUserByOpenId(wxUserAuthBo.getOpenId(), UserEntity.USER_TYPE_ADMIN);
                Long id;
                if (userEntity == null) {
                    WxUserInfoBo wxUserInfoBo = wxApiService.getUserInfo(wxUserAuthBo.getAccessToken(), wxUserAuthBo.getOpenId());
                    userEntity = new UserEntity();
                    userEntity.setUserType(UserEntity.USER_TYPE_ADMIN);
                    userEntity.setWxLogo(wxUserInfoBo.getHeadimgurl());
                    userEntity.setWxOpenId(wxUserAuthBo.getOpenId());
                    userEntity.setWxNickName(wxUserInfoBo.getNickName());
                    userEntity.setUserStatus(UserEntity.USER_STATUS_NORMAL);
                    id = userService.createUser(userEntity);
                } else {
                    id = userEntity.getId();
                }
                UserCookieUtil.addCookie(response, UserEntity.USER_TYPE_ADMIN, id, zbxConfig.getEncryptKey());
                // 新增回收请求
                response.sendRedirect(zbxConfig.getZbxServiceDomain());
                return null;
            }
            return null;
        } catch (BizException e) {
            logger.warn("", e);
            ModelAndView modelAndView = new ModelAndView("/error");
            modelAndView.addObject("code", e.getErrorCode().getCode());
            modelAndView.addObject("msg", e.getErrorCode().getCode());
            return modelAndView;
        } catch (Exception e) {
            logger.warn("", e);
            ModelAndView modelAndView = new ModelAndView("/error");
            modelAndView.addObject("code", ErrorCode.CODE_UNKONWN_ERROR.getCode());
            modelAndView.addObject("msg", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("home")
    public ModelAndView index(@RequestParam("type") Integer type, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (Objects.equals(UserEntity.USER_TYPE_YEZHU, type)) {
                UserCookieVo userCookieVo;
                try {
                    userCookieVo = UserCookieUtil.parserCookie(request, zbxConfig.getEncryptKey());
                } catch (Exception e) {
                    UserCookieUtil.delCookie(response);
                    logger.warn("", e);
                    // cookie解析报错，走微信授权
                    String authUrl = MessageFormat.format(wxPropConfig.getAuthRedirectUrl(), wxPropConfig.getAppId(), URLEncoder.encode(zbxConfig.getZbxServiceDomain() + "/weixin/redirectIndex", "utf-8"), UserEntity.USER_TYPE_YEZHU);
                    response.sendRedirect(authUrl);
                    return null;
                }
                if (userCookieVo == null) {
                    String authUrl = MessageFormat.format(wxPropConfig.getAuthRedirectUrl(), wxPropConfig.getAppId(), URLEncoder.encode(zbxConfig.getZbxServiceDomain() + "/weixin/redirectIndex", "utf-8"), UserEntity.USER_TYPE_YEZHU);
                    response.sendRedirect(authUrl);
                    return null;
                }
                UserEntity userEntity = userService.findById(userCookieVo.getUid());
                // 如果用户不存在，则重定向到授权页
                if (userEntity == null || !Objects.equals(UserEntity.USER_TYPE_YEZHU, userEntity.getUserType())) {
                    UserCookieUtil.delCookie(response);
                    String authUrl = MessageFormat.format(wxPropConfig.getAuthRedirectUrl(), wxPropConfig.getAppId(), URLEncoder.encode(zbxConfig.getZbxServiceDomain() + "/weixin/redirectIndex", "utf-8"), UserEntity.USER_TYPE_YEZHU);
                    response.sendRedirect(authUrl);
                    return null;
                }
                // 解密正常，跳转首页
                response.sendRedirect(zbxConfig.getZbxServiceDomain());
                return null;
            } else if (Objects.equals(UserEntity.USER_TYPE_HUISHOU, type)) {
                UserCookieVo userCookieVo;
                try {
                    userCookieVo = UserCookieUtil.parserCookie(request, zbxConfig.getEncryptKey());
                } catch (Exception e) {
                    UserCookieUtil.delCookie(response);
                    logger.warn("", e);
                    // cookie解析报错，走微信授权
                    String authUrl = MessageFormat.format(wxPropConfig.getAuthRedirectUrl(), wxPropConfig.getAppId(), URLEncoder.encode(zbxConfig.getZbxServiceDomain() + "/weixin/redirectIndex", "utf-8"), UserEntity.USER_TYPE_HUISHOU);
                    response.sendRedirect(authUrl);
                    return null;
                }
                if (userCookieVo == null) {
                    String authUrl = MessageFormat.format(wxPropConfig.getAuthRedirectUrl(), wxPropConfig.getAppId(), URLEncoder.encode(zbxConfig.getZbxServiceDomain() + "/weixin/redirectIndex", "utf-8"), UserEntity.USER_TYPE_HUISHOU);
                    response.sendRedirect(authUrl);
                    return null;
                }
                UserEntity userEntity = userService.findById(userCookieVo.getUid());
                // 如果用户不存在，则重定向到授权页
                if (userEntity == null || !Objects.equals(UserEntity.USER_TYPE_HUISHOU, userEntity.getUserType())) {
                    UserCookieUtil.delCookie(response);
                    String authUrl = MessageFormat.format(wxPropConfig.getAuthRedirectUrl(), wxPropConfig.getAppId(), URLEncoder.encode(zbxConfig.getZbxServiceDomain() + "/weixin/redirectIndex", "utf-8"), UserEntity.USER_TYPE_HUISHOU);
                    response.sendRedirect(authUrl);
                    return null;
                }
                return judgeHuishouUser(response, userEntity.getUserStatus());
            } else if (Objects.equals(UserEntity.USER_TYPE_ADMIN, type)) {
                UserCookieVo userCookieVo;
                try {
                    userCookieVo = UserCookieUtil.parserCookie(request, zbxConfig.getEncryptKey());
                } catch (Exception e) {
                    UserCookieUtil.delCookie(response);
                    logger.warn("", e);
                    // cookie解析报错，走微信授权
                    String authUrl = MessageFormat.format(wxPropConfig.getAuthRedirectUrl(), wxPropConfig.getAppId(), URLEncoder.encode(zbxConfig.getZbxServiceDomain() + "/weixin/redirectIndex", "utf-8"), UserEntity.USER_TYPE_ADMIN);
                    response.sendRedirect(authUrl);
                    return null;
                }
                if (userCookieVo == null) {
                    String authUrl = MessageFormat.format(wxPropConfig.getAuthRedirectUrl(), wxPropConfig.getAppId(), URLEncoder.encode(zbxConfig.getZbxServiceDomain() + "/weixin/redirectIndex", "utf-8"), UserEntity.USER_TYPE_ADMIN);
                    response.sendRedirect(authUrl);
                    return null;
                }
                UserEntity userEntity = userService.findById(userCookieVo.getUid());
                // 如果不是管理员，则按照没有cookie处理
                if (userEntity == null || !Objects.equals(UserEntity.USER_TYPE_ADMIN, userEntity.getUserType())) {
                    UserCookieUtil.delCookie(response);
                    String authUrl = MessageFormat.format(wxPropConfig.getAuthRedirectUrl(), wxPropConfig.getAppId(), URLEncoder.encode(zbxConfig.getZbxServiceDomain() + "/weixin/redirectIndex", "utf-8"), UserEntity.USER_TYPE_ADMIN);
                    response.sendRedirect(authUrl);
                    return null;
                }
                String index = zbxConfig.getZbxServiceDomain();
                response.sendRedirect(index);
                return null;
            }
            return null;
        } catch (Exception e) {
            logger.warn("", e);
            ModelAndView modelAndView = new ModelAndView("/error");
            modelAndView.addObject("code", ErrorCode.CODE_UNKONWN_ERROR.getCode());
            modelAndView.addObject("msg", e.getMessage());
            return modelAndView;
        }
    }

    /**
     * 回收人员，根据用户状态跳转不同页面
     *
     * @param response
     * @param userStatus
     * @return
     * @throws IOException
     */
    private ModelAndView judgeHuishouUser(HttpServletResponse response, Integer userStatus) throws IOException {
        // 正常，跳转回收列表页面
        if (Objects.equals(UserEntity.USER_STATUS_NORMAL, userStatus)) {
            String index = zbxConfig.getZbxServiceDomain();
            response.sendRedirect(index);
            return null;
        }
        // 审核中,跳转审核中等待页
        else if (Objects.equals(UserEntity.USER_STATUS_PERMIT_PROCESS, userStatus)) {
            String index = zbxConfig.getZbxServiceDomain();
            response.sendRedirect(index);
            return null;
        }
        // 还没提交审核，提醒用户提交头像审核
        else if (Objects.equals(UserEntity.USER_STATUS_NEED_ACTIVE, userStatus)) {
            String index = zbxConfig.getZbxServiceDomain();
            // 还没提交过审核，走提交流程
            response.sendRedirect(index + "/#/register");
            return null;
        }
        // 审核不通过，重新提交
        else if (Objects.equals(UserEntity.USER_STATUS_NOT_PERMIT, userStatus)) {
            String index = zbxConfig.getZbxServiceDomain();
            response.sendRedirect(index);
            return null;
        }
        // 禁用或者注销,跳转状态异常页面
        else {
            String index = zbxConfig.getZbxServiceDomain();
            response.sendRedirect(index);
            return null;
        }
    }
}
