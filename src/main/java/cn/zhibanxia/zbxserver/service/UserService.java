package cn.zhibanxia.zbxserver.service;

import cn.zhibanxia.zbxserver.entity.UserEntity;

/**
 * Created by zzy on  2018/10/02 13:02
 */
public interface UserService {
    /**
     * 根据openId和类型查询用户
     *
     * @param openId
     * @param userType
     * @return
     */
    UserEntity findUserByOpenId(String openId, int userType);

    /**
     * 新增用户
     *
     * @param userEntity
     * @return
     */
    Long createUser(UserEntity userEntity);

    /**
     * 根据uid查询
     *
     * @param uid
     * @return
     */
    UserEntity findById(Long uid);


    /**
     * 增加手机号、审核头像
     *
     * @return
     */
    boolean addMobileAndVerify(Long id, String mobilePhone, String verifyLogo);
}
