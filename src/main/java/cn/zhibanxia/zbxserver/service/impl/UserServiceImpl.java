package cn.zhibanxia.zbxserver.service.impl;

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
    public boolean addMobileAndVerify(Long id, String mobilePhone, String verifyLogo) {
        return userDao.addMobileAndVerify(id, mobilePhone, verifyLogo);
    }

    @Override
    public List<UserEntity> listAllUser(int startPage, int endPage) {
        return userDao.listAllUser(startPage, endPage);
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
    public boolean updateUserStatus(Long id, int userSatus) {
        return userDao.updateUserStatus(id, userSatus);
    }
}
