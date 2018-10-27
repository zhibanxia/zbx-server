package cn.zhibanxia.zbxserver.util;

import cn.zhibanxia.zbxserver.config.ZbxConfig;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zzy on  2018/10/02 14:16
 */
@Component
public class RequestUtil implements InitializingBean {
    @Autowired
    private ZbxConfig zbxConfig;
    private static String cookieDomain;
    private static final int SEVEN_DAYS = 7 * 24 * 3600;

    /**
     * 获取cookie
     *
     * @param request
     * @param key
     * @return
     */
    public static String getCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (key.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * 添加用户cookie
     *
     * @param response
     * @param key
     * @param value
     */
    public static void addUserCookie(HttpServletResponse response, String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setDomain(cookieDomain);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(SEVEN_DAYS);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 删除用户cookie
     *
     * @param response
     * @param key
     */
    public static void delUserCookie(HttpServletResponse response, String key) {
        Cookie cookie = new Cookie(key, "");
        cookie.setDomain(cookieDomain);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    public void afterPropertiesSet() {
        setCookieDomain(zbxConfig.getCookieDomain());
    }

    private static void setCookieDomain(String cookieDomain) {
        RequestUtil.cookieDomain = cookieDomain;
    }
}
