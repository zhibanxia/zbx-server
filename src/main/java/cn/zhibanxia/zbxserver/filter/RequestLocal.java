package cn.zhibanxia.zbxserver.filter;

import cn.zhibanxia.zbxserver.entity.UserEntity;
import cn.zhibanxia.zbxserver.service.UserService;
import cn.zhibanxia.zbxserver.util.UserCookieUtil;
import cn.zhibanxia.zbxserver.controller.param.UserCookieVo;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zzy on  2018/10/03 11:45
 */
public class RequestLocal {
    private static ThreadLocal<RequestLocal> threadLocal = new ThreadLocal<>();
    private Long yezhuUid;
    private Long huishouUid;
    private UserEntity yezhuUserEntity;
    private UserEntity huishouUserEntity;

    private RequestLocal() {
    }

    /**
     * 解析request
     *
     * @param request
     * @param userService
     * @param encryptKey
     */
    static void init(HttpServletRequest request, UserService userService, String encryptKey) {
        RequestLocal requestLocal = new RequestLocal();
        UserCookieVo yezhuUserCookieVo = UserCookieUtil.parserCookie(request, UserEntity.USER_TYPE_YEZHU, encryptKey);
        if (yezhuUserCookieVo != null) {
            requestLocal.yezhuUid = yezhuUserCookieVo.getUid();
            requestLocal.yezhuUserEntity = userService.findById(yezhuUserCookieVo.getUid());
        }
        UserCookieVo huishouUserCookieVo = UserCookieUtil.parserCookie(request, UserEntity.USER_TYPE_YEZHU, encryptKey);
        if (huishouUserCookieVo != null) {
            requestLocal.huishouUid = huishouUserCookieVo.getUid();
            requestLocal.huishouUserEntity = userService.findById(huishouUserCookieVo.getUid());
        }
        threadLocal.set(requestLocal);
    }

    public static RequestLocal get() {
        return threadLocal.get();
    }

    /**
     * 是否是业主
     *
     * @return
     */
    public boolean isYezhu() {
        return yezhuUid != null && yezhuUserEntity != null;
    }

    /**
     * 是否是回收人员
     *
     * @return
     */
    public boolean isHuishou() {
        return huishouUid != null && huishouUserEntity != null;
    }


    public Long getYezhuUid() {
        return yezhuUid;
    }

    public Long getHuishouUid() {
        return huishouUid;
    }

    public UserEntity getYezhuUserEntity() {
        return yezhuUserEntity;
    }

    public UserEntity getHuishouUserEntity() {
        return huishouUserEntity;
    }
}
