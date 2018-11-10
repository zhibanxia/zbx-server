package cn.zhibanxia.zbxserver.filter;

import cn.zhibanxia.zbxserver.annotation.NotLoginCanAccess;
import cn.zhibanxia.zbxserver.config.ZbxConfig;
import cn.zhibanxia.zbxserver.constant.UrlConstant;
import cn.zhibanxia.zbxserver.service.UserService;
import cn.zhibanxia.zbxserver.util.RequestUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * Created by zzy on  2018/10/03 11:39
 */
public class LoginFilter implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger("userAccess");

    private final UserService userService;
    private final ZbxConfig zbxConfig;

    public LoginFilter(UserService userService, ZbxConfig zbxConfig) {
        this.userService = userService;
        this.zbxConfig = zbxConfig;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                // ctrl或者方法有NoLoginCanAccess注解则不做登录校验,不必关心注解值
                if (handlerMethod.getBeanType().isAnnotationPresent(NotLoginCanAccess.class) || handlerMethod.getMethod().isAnnotationPresent(NotLoginCanAccess.class)) {
                    return true;
                }
            }
            // 初始化requestLocal
            RequestLocal.init(request, userService, zbxConfig.getEncryptKey(), zbxConfig.getAdminOpenIdSet());
            // 既没有业主cookie，又没有回收人员cookie，重定向到错误页
            if (!(RequestLocal.get().isYezhu() || RequestLocal.get().isHuishou() || RequestLocal.get().isAdmin())) {
                response.sendRedirect(zbxConfig.getZbxServiceDomain() + UrlConstant.NOT_ALLOW);
                return false;
            }
            // 记录管理员cookie
            logCookie(request);
            return true;
        } catch (Exception e) {
            logger.warn("", e);
            return false;
        }
    }

    private void logCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isEmpty(cookies)) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("ip=").append(RequestUtil.getIpAddr(request)).append(", ");
        builder.append("User-Agent=").append(request.getHeader("User-Agent")).append(", ");
        builder.append('[');
        for (Cookie cookie : cookies) {
            builder.append(cookie.getName()).append('=').append(cookie.getValue()).append(", ");
        }
        builder.append(']');
        logger.info(builder.toString());
    }
}
