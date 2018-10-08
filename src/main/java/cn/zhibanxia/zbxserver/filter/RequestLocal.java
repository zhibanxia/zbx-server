package cn.zhibanxia.zbxserver.filter;

import cn.zhibanxia.zbxserver.entity.UserEntity;
import cn.zhibanxia.zbxserver.service.UserService;
import cn.zhibanxia.zbxserver.util.UserCookieUtil;
import cn.zhibanxia.zbxserver.controller.param.UserCookieVo;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Set;

/**
 * Created by zzy on  2018/10/03 11:45
 */
public class RequestLocal {
    private static ThreadLocal<RequestLocal> threadLocal = new ThreadLocal<>();
    private Integer userType;
    private Long yezhuUid;
    private Long huishouUid;
    private Long adminUid;
    private UserEntity yezhuUserEntity;
    private UserEntity huishouUserEntity;
    private UserEntity adminUserEntity;

    private RequestLocal() {
    }

    /**
     * 解析request
     *
     * @param request
     * @param userService
     * @param encryptKey
     */
    static void init(HttpServletRequest request, UserService userService, String encryptKey, Set<String> adminOpenIds) {
        RequestLocal requestLocal = new RequestLocal();
        threadLocal.set(requestLocal);

        UserCookieVo userCookieVo = UserCookieUtil.parserCookie(request, encryptKey);
        if (userCookieVo == null) {
            return;
        }
        requestLocal.userType = userCookieVo.getType();
        if (Objects.equals(UserEntity.USER_TYPE_YEZHU, userCookieVo.getType())) {
            requestLocal.yezhuUid = userCookieVo.getUid();
            requestLocal.yezhuUserEntity = userService.findById(userCookieVo.getUid());
        } else if (Objects.equals(UserEntity.USER_TYPE_HUISHOU, userCookieVo.getType())) {
            requestLocal.huishouUid = userCookieVo.getUid();
            requestLocal.huishouUserEntity = userService.findById(userCookieVo.getUid());
        } else if (Objects.equals(UserEntity.USER_TYPE_ADMIN, userCookieVo.getType())) {
            requestLocal.adminUid = userCookieVo.getUid();
            requestLocal.adminUserEntity = userService.findById(userCookieVo.getUid());
            boolean isAdmin = checkAdmin(requestLocal.adminUserEntity, adminOpenIds);
            if (!isAdmin) {
                requestLocal.adminUid = null;
                requestLocal.adminUserEntity = null;
            }
        }
    }

    public static RequestLocal get() {
        return threadLocal.get();
    }

    /**
     * 是否是业主，uid不为null，用户存在，并且状态是正常态
     *
     * @return
     */
    public boolean isYezhu() {
        return yezhuUid != null && yezhuUserEntity != null && Objects.equals(yezhuUserEntity.getUserStatus(), UserEntity.USER_STATUS_NORMAL);
    }

    /**
     * 是否是回收人员
     *
     * @return
     */
    public boolean isHuishou() {
        return huishouUid != null && huishouUserEntity != null;
    }

    public boolean isAdmin() {
        return adminUid != null && adminUserEntity != null;
    }

    private static boolean checkAdmin(UserEntity adminUserEntity, Set<String> openIds) {
        if (adminUserEntity == null) {
            return false;
        }
        return openIds.contains(adminUserEntity.getWxOpenId());
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

    public Integer getUserType() {
        return userType;
    }

    public Long getAdminUid() {
        return adminUid;
    }

    public UserEntity getAdminUserEntity() {
        return adminUserEntity;
    }
}
