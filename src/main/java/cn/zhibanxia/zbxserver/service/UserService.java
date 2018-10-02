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
}
