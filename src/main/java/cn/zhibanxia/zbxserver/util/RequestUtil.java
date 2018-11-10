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


    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.trim().length() > 0) {
            String[] ips = ip.trim().split(",");
            int size = ips.length;
            if (size > 0) {
                ip = ips[0].trim();
            }
        }

        if (isInvalidIp(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (isInvalidIp(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (isInvalidIp(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (isInvalidIp(ip)) {
            ip = request.getHeader("Cdn-Src-Ip");
        }

        if (isInvalidIp(ip)) {
            ip = request.getRemoteAddr();
        }

        if (ip != null && ip.startsWith("0:0:0:0")) {
            ip = "127.0.0.1";
        }

        return ip;
    }


    private static boolean isInvalidIp(String ip) {
        return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
    }

    public static boolean isProxy(HttpServletRequest request) {
        String httpVia = request.getHeader("HTTP_VIA");
        if (httpVia != null && !"".equals(httpVia)) {
            return true;
        } else {
            String xForwardedFor = request.getHeader("x-forwarded-for");
            if (xForwardedFor != null && xForwardedFor.trim().length() > 0) {
                String[] ips = xForwardedFor.trim().split(",");
                long sizeOfForwardFor = (long) ips.length;
                if (sizeOfForwardFor > 1L) {
                    return true;
                }
            }

            return false;
        }
    }

    @Override
    public void afterPropertiesSet() {
        setCookieDomain(zbxConfig.getCookieDomain());
    }

    private static void setCookieDomain(String cookieDomain) {
        RequestUtil.cookieDomain = cookieDomain;
    }
}
