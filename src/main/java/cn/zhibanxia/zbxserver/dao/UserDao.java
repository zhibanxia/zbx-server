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
     * 根据主键id查询列表
     *
     * @param ids
     * @return
     */
    List<UserEntity> selectByIds(List<Long> ids);

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
     * @param userStatus
     * @return
     */
    boolean updateUserStatus(Long id, int userStatus);

    /**
     * 增加手机号、审核头像
     *
     * @return
     */
    boolean addMobileAndVerify(Long id, String mobilePhone, String verifyLogo, Integer wxNotifyFlag, Integer voiceNotifyFlag);


    /**
     * 分页查询全部用户列表
     *
     * @param startPage
     * @param endPage
     * @return
     */
    List<UserEntity> listAllUser(int startPage, int endPage);

    /**
     * 查询用户总数
     *
     * @return
     */
    int countAllUser();


    /**
     * 审核回收人员
     *
     * @param id           回收人员id
     * @param verifyResult 审核结果，true通过，false失败
     * @param remark       审核失败原因，可选
     * @return
     */
    boolean verifyHuishou(Long id, boolean verifyResult, String remark);

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    boolean delete(Long id);
}
