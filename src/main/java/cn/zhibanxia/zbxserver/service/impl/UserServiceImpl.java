package cn.zhibanxia.zbxserver.service.impl;

import cn.zhibanxia.zbxserver.bo.SearchUserBo;
import cn.zhibanxia.zbxserver.dao.UserDao;
import cn.zhibanxia.zbxserver.entity.UserEntity;
import cn.zhibanxia.zbxserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zzy on  2018/10/02 13:35
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserEntity findUserByOpenId(String openId, int userType) {
        return userDao.selectUserByOpenIdAndType(openId, userType);
    }

    @Override
    public Long createUser(UserEntity userEntity) {
        return userDao.insertUser(userEntity);
    }

    @Override
    public UserEntity findById(Long uid) {
        return userDao.selectById(uid);
    }

    @Override
    public boolean addMobileAndVerify(Long id, String mobilePhone, String verifyLogo, Integer wxNotifyFlag, Integer voiceNotifyFlag) {
        return userDao.addMobileAndVerify(id, mobilePhone, verifyLogo, wxNotifyFlag, voiceNotifyFlag);
    }

    @Override
    public List<UserEntity> listAllUser(int startPage, int endPage) {
        return userDao.listAllUser(startPage, endPage);
    }

    @Override
    public List<UserEntity> searchUser(SearchUserBo searchUserBo) {
        return userDao.searchUser(searchUserBo);
    }

    @Override
    public int countSearchUser(SearchUserBo searchUserBo) {
        return userDao.countSearchUser(searchUserBo);
    }

    @Override
    public int countAllUser() {
        return userDao.countAllUser();
    }

    @Override
    public boolean verifyHuishou(Long id, boolean verifyResult, String remark) {
        return userDao.verifyHuishou(id, verifyResult, remark);
    }

    @Override
    public boolean updateUserStatus(Long id, int userStatus) {
        return userDao.updateUserStatus(id, userStatus);
    }

    @Override
    public boolean delete(Long id) {
        return userDao.delete(id);
    }

    @Override
    public boolean updateNoticeConfig(Long id, Integer wxNotifyFlag, Integer voiceNotifyFlag) {
        return userDao.addMobileAndVerify(id, null, null, wxNotifyFlag, voiceNotifyFlag);
    }
}
