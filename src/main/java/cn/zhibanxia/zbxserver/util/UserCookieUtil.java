package cn.zhibanxia.zbxserver.util;

import cn.zhibanxia.zbxserver.constant.CookieConstant;
import cn.zhibanxia.zbxserver.entity.UserEntity;
import cn.zhibanxia.zbxserver.controller.param.UserCookieVo;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzy on  2018/10/03 11:58
 */
public class UserCookieUtil {
    private static final ImmutableMap<Integer, String> userTypeCookieNameMap;

    static {
        Map<Integer, String> map = new HashMap<>();
        map.put(UserEntity.USER_TYPE_YEZHU, CookieConstant.COOKIE_KEY_YEZHU_USER);
        map.put(UserEntity.USER_TYPE_HUISHOU, CookieConstant.COOKIE_KEY_HUISHOU_USER);
        map.put(UserEntity.USER_TYPE_ADMIN, CookieConstant.COOKIE_KEY_ADMIN_USER);
        userTypeCookieNameMap = ImmutableMap.copyOf(map);
    }

    public static UserCookieVo parserCookie(HttpServletRequest request, int userType, String encryptKey) {
        String cookieValue = RequestUtil.getCookie(request, userTypeCookieNameMap.get(userType));
        if (cookieValue == null) {
            return null;
        }
        return JSONObject.parseObject(EncryptUtil.decrypt(cookieValue, encryptKey), UserCookieVo.class);
    }

    public static void addCookie(HttpServletResponse response, int userType, Long uid, String encryptKey) {
        UserCookieVo userCookieVo = new UserCookieVo();
        userCookieVo.setUid(uid);
        userCookieVo.setTs(System.currentTimeMillis());
        String cookieVal = EncryptUtil.encrypt(JSONObject.toJSONString(userCookieVo), encryptKey);
        RequestUtil.addUserCookie(response, userTypeCookieNameMap.get(userType), cookieVal);
    }
}
