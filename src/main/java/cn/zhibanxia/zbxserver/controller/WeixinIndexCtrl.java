package cn.zhibanxia.zbxserver.controller;

import cn.zhibanxia.zbxserver.bo.WxUserAuthBo;
import cn.zhibanxia.zbxserver.bo.WxUserInfoBo;
import cn.zhibanxia.zbxserver.config.WxPropConfig;
import cn.zhibanxia.zbxserver.constant.CookieConstant;
import cn.zhibanxia.zbxserver.constant.ErrorCodeConstant;
import cn.zhibanxia.zbxserver.entity.UserEntity;
import cn.zhibanxia.zbxserver.exception.BizException;
import cn.zhibanxia.zbxserver.service.UserService;
import cn.zhibanxia.zbxserver.service.WxApiService;
import cn.zhibanxia.zbxserver.util.RequestUtil;
import cn.zhibanxia.zbxserver.vo.UserCookieVo;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Objects;

/**
 * Created by zzy on  2018/10/02 09:34
 */
@RestController("/weixin")
public class WeixinIndexCtrl {

    private static Logger logger = LoggerFactory.getLogger(WeixinIndexCtrl.class);
    @Autowired
    private WxApiService wxApiService;
    @Autowired
    private UserService userService;
    @Autowired
    private WxPropConfig wxPropConfig;

    /**
     * 重定向到业主页
     *
     * @param code
     * @param state
     * @return
     */
    @GetMapping(value = "redirectIndex")
    public ModelAndView redirectYezhuIndex(@RequestParam("code") String code, @RequestParam("state") String state) {
        try {
            WxUserAuthBo wxUserAuthBo = wxApiService.userAuth(code);
            UserEntity userEntity = userService.findUserByOpenId(wxUserAuthBo.getOpenId(), UserEntity.USER_TYPE_YEZHU);
            if (userEntity == null) {
                WxUserInfoBo wxUserInfoBo = wxApiService.getUserInfo(wxUserAuthBo.getAccessToken(), wxUserAuthBo.getOpenId());
                userEntity = new UserEntity();
                userEntity.setUserType(UserEntity.USER_TYPE_YEZHU);
                userEntity.setWxLogo(wxUserInfoBo.getHeadimgurl());
                userEntity.setWxOpenId(wxUserAuthBo.getOpenId());
                userEntity.setWxNickName(wxUserInfoBo.getNickName());
                userEntity.setUserStatus(UserEntity.USER_STATUS_NORMAL);
                Long id = userService.createUser(userEntity);
            }
            return null;
        } catch (BizException e) {
            logger.warn("", e);
            ModelAndView modelAndView = new ModelAndView("/error");
            modelAndView.addObject("code", e.getCode());
            modelAndView.addObject("msg", e.getMsg());
            return modelAndView;
        } catch (Exception e) {
            logger.warn("", e);
            ModelAndView modelAndView = new ModelAndView("/error");
            modelAndView.addObject("code", ErrorCodeConstant.CODE_UNKONWN_ERROR);
            modelAndView.addObject("msg", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("home")
    public ModelAndView index(@RequestParam("type") Integer type, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (Objects.equals(UserEntity.USER_TYPE_YEZHU, type)) {
                String cookieValue = RequestUtil.getCookie(request, CookieConstant.COOKIE_KEY_YEZHU_USER);
                if (StringUtils.isEmpty(cookieValue)) {
                    // 没有cookie，走微信授权
                    String authUrl = MessageFormat.format(WxApiService.AUTH_REDIRECT_URL, wxPropConfig.getAppId(), URLEncoder.encode("https://www.zhibanxia.cn/weixin/redirectIndex", "utf-8"), UserEntity.USER_TYPE_YEZHU);
                    response.sendRedirect(authUrl);
                    return null;
                }
                UserCookieVo userCookieVo;
                try {
                    userCookieVo = JSONObject.parseObject(cookieValue, UserCookieVo.class);
                } catch (Exception e) {
                    logger.warn("", e);
                    // cookie解析报错，走微信授权
                    String authUrl = MessageFormat.format(WxApiService.AUTH_REDIRECT_URL, wxPropConfig.getAppId(), URLEncoder.encode("https://www.zhibanxia.cn/weixin/redirectIndex", "utf-8"), UserEntity.USER_TYPE_YEZHU);
                    response.sendRedirect(authUrl);
                    return null;
                }
            } else if (Objects.equals(UserEntity.USER_TYPE_YEZHU, type)) {
                String cookieValue = RequestUtil.getCookie(request, CookieConstant.COOKIE_KEY_HUISHOU_USER);
                if (StringUtils.isEmpty(cookieValue)) {
                    // 没有cookie，走微信授权
                    String authUrl = MessageFormat.format(WxApiService.AUTH_REDIRECT_URL, wxPropConfig.getAppId(), URLEncoder.encode("https://www.zhibanxia.cn/weixin/redirectIndex", "utf-8"), UserEntity.USER_TYPE_HUISHOU);
                    response.sendRedirect(authUrl);
                    return null;
                }
                UserCookieVo userCookieVo;
                try {
                    userCookieVo = JSONObject.parseObject(cookieValue, UserCookieVo.class);
                } catch (Exception e) {
                    logger.warn("", e);
                    // cookie解析报错，走微信授权
                    String authUrl = MessageFormat.format(WxApiService.AUTH_REDIRECT_URL, wxPropConfig.getAppId(), URLEncoder.encode("https://www.zhibanxia.cn/weixin/redirectIndex", "utf-8"), UserEntity.USER_TYPE_HUISHOU);
                    response.sendRedirect(authUrl);
                    return null;
                }
                UserEntity userEntity = userService.findById(userCookieVo.getUid());
                // 如果用户不存在，或者类型不是回收人员，则重定向到授权页
                if (userEntity == null || Objects.equals(userEntity.getUserType(), UserEntity.USER_TYPE_HUISHOU)) {
                    // cookie解析报错，走微信授权
                    String authUrl = MessageFormat.format(WxApiService.AUTH_REDIRECT_URL, wxPropConfig.getAppId(), URLEncoder.encode("https://www.zhibanxia.cn/weixin/redirectIndex", "utf-8"), UserEntity.USER_TYPE_HUISHOU);
                    response.sendRedirect(authUrl);
                    return null;
                }
                // 正常，跳转回收列表页面
                if (Objects.equals(UserEntity.USER_STATUS_NORMAL, userEntity.getUserStatus())) {
                    String recyleListUrl = "https://www.zhibanxia.cn/recyle/list?bizType=1";
                    response.sendRedirect(recyleListUrl);
                    return null;
                }
                // 审核中,跳转审核中等待页
                else if (Objects.equals(UserEntity.USER_STATUS_PERMIT_PROCESS, userEntity.getUserStatus())) {
                    ModelAndView modelAndView = new ModelAndView("/user/permit_process");
                    return modelAndView;
                }
                // 还没提交审核，提醒用户提交头像审核
                else if (Objects.equals(UserEntity.USER_STATUS_NEED_ACTIVE, userEntity.getUserStatus())) {
                    return null;
                }
                // 审核不通过，重新提交
                else if (Objects.equals(UserEntity.USER_STATUS_NOT_PERMIT, userEntity.getUserStatus())) {
                    return null;
                }
                // 禁用或者注销,跳转状态异常页面
                else {
                    ModelAndView modelAndView = new ModelAndView("/user/problem");
                    return modelAndView;
                }
            }
            return null;
        } catch (Exception e) {
            logger.warn("", e);
            ModelAndView modelAndView = new ModelAndView("/error");
            modelAndView.addObject("code", ErrorCodeConstant.CODE_UNKONWN_ERROR);
            modelAndView.addObject("msg", e.getMessage());
            return modelAndView;
        }
    }
}
