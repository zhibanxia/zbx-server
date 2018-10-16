package cn.zhibanxia.zbxserver.service;

import cn.zhibanxia.zbxserver.entity.UserEntity;

import java.util.List;

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
     * 更新用户状态
     *
     * @param id
     * @param userSatus
     * @return
     */
    boolean updateUserStatus(Long id, int userSatus);
}
