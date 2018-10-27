package cn.zhibanxia.zbxserver.util;

import cn.zhibanxia.zbxserver.constant.CookieConstant;
import cn.zhibanxia.zbxserver.controller.param.UserCookieVo;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zzy on  2018/10/03 11:58
 */
public class UserCookieUtil {

    public static UserCookieVo parserCookie(HttpServletRequest request, String encryptKey) {
        String cookieValue = RequestUtil.getCookie(request, CookieConstant.COOKIE_KEY_YEZHU_USER);
        if (cookieValue == null) {
            return null;
        }
        return JSONObject.parseObject(EncryptUtil.decrypt(cookieValue, encryptKey), UserCookieVo.class);
    }

    public static void addCookie(HttpServletResponse response, int userType, Long uid, String encryptKey) {
        UserCookieVo userCookieVo = new UserCookieVo();
        userCookieVo.setUid(uid);
        userCookieVo.setType(userType);
        userCookieVo.setTs(System.currentTimeMillis());
        String cookieVal = EncryptUtil.encrypt(JSONObject.toJSONString(userCookieVo), encryptKey);
        RequestUtil.addUserCookie(response, CookieConstant.COOKIE_KEY_YEZHU_USER, cookieVal);
    }

    public static void delCookie(HttpServletResponse response) {
        RequestUtil.delUserCookie(response, CookieConstant.COOKIE_KEY_YEZHU_USER);
    }
}
