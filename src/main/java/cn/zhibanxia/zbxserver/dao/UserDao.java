package cn.zhibanxia.zbxserver.dao;


import cn.zhibanxia.zbxserver.entity.UserEntity;

import java.util.List;

public interface UserDao {

    /**
     * 新增
     *
     * @param userEntity
     * @return
     */
    Long insertUser(UserEntity userEntity);

    /**
     * 根据主键id查询
     *
     * @param id
     * @return
     */
    UserEntity selectById(Long id);

    /**
     * 根据wOpenId和类型查找用户
     *
     * @param wxOpenId
     * @param userType
     * @return
     */
    UserEntity selectUserByOpenIdAndType(String wxOpenId, Integer userType);

    /**
     * 根据wxOpenId查询用户列表
     *
     * @param wxOpenId
     * @return
     */
    List<UserEntity> selectUsersByOpenId(String wxOpenId);

    /**
     * 更新审核头像，同时更新状态
     *
     * @param id
     * @param verifyLogo
     * @return
     */
    boolean updateUserVerifyLogo(Long id, String verifyLogo);

    /**
     * 更新用户状态
     *
     * @param id
     * @param userSatus
     * @return
     */
    boolean updateUserStatus(Long id, int userSatus);

    /**
     * 增加手机号、审核头像
     *
     * @return
     */
    boolean addMobileAndVerify(Long id, String mobilePhone, String verifyLogo);
}
