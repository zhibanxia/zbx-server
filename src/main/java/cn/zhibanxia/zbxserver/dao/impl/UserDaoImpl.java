package cn.zhibanxia.zbxserver.dao.impl;

import cn.zhibanxia.zbxserver.dao.BaseDao;
import cn.zhibanxia.zbxserver.dao.UserDao;
import cn.zhibanxia.zbxserver.entity.UserEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzy on  2018/09/24 21:01
 */
@Component
public class UserDaoImpl extends BaseDao implements UserDao {
    @Override
    public Long insertUser(UserEntity userEntity) {
        insert("insert", userEntity);
        return userEntity.getId();
    }

    @Override
    public UserEntity selectById(Long id) {
        return selectOne("selectById", id);
    }

    @Override
    public UserEntity selectUserByOpenIdAndType(String wxOpenId, Integer type) {
        List<UserEntity> list = selectUsersByOpenIdOrType(wxOpenId, type);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public List<UserEntity> selectUsersByOpenId(String wxOpenId) {
        return selectUsersByOpenIdOrType(wxOpenId, null);
    }

    @Override
    public boolean updateUserVerifyLogo(Long id, String verifyLogo) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("verifyLogo", verifyLogo);
        int ret = update("updateUserVerifyLogo", params);
        return ret >= 1;
    }

    @Override
    public boolean updateUserStatus(Long id, int userSatus) {
        int ret = update("updateUserStatus", userSatus);
        return ret >= 1;
    }

    private List<UserEntity> selectUsersByOpenIdOrType(String wxOpenId, Integer userType) {
        Map<String, Object> params = new HashMap<>();
        params.put("wxOpenId", wxOpenId);
        params.put("userType", userType);
        return selectList("selectUsersByOpenIdOrType", params);
    }
}
