package cn.zhibanxia.zbxserver.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zzy on  2018/10/02 14:16
 */
public class RequestUtil {
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
        cookie.setDomain("zhibanxia.cn");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(SEVEN_DAYS);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
