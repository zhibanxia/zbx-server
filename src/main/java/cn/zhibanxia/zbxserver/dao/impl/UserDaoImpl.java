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
    public List<UserEntity> selectByIds(List<Long> ids) {
        return selectList("selectByIds", ids);
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
    public boolean updateUserStatus(Long id, int userStatus) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("userStatus", userStatus);
        int ret = update("updateUserStatus", params);
        return ret >= 1;
    }

    @Override
    public boolean addMobileAndVerify(Long id, String mobilePhone, String verifyLogo) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("mobilePhone", mobilePhone);
        params.put("verifyLogo", verifyLogo);
        int ret = update("addMobileAndVerify", params);
        return ret >= 1;
    }

    @Override
    public List<UserEntity> listAllUser(int startPage, int endPage) {
        Map<String, Object> params = new HashMap<>();
        params.put("startPage", startPage);
        params.put("endPage", endPage);
        return selectList("listAllUser", params);
    }

    @Override
    public int countAllUser() {
        return selectOne("countAllUser");
    }

    @Override
    public boolean verifyHuishou(Long id, boolean verifyResult, String remark) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("userStatus", verifyResult ? UserEntity.USER_STATUS_NORMAL : UserEntity.USER_STATUS_NOT_PERMIT);
        params.put("verifyRemark", remark);
        int ret = update("verifyHuishou", params);
        return ret == 1;
    }

    private List<UserEntity> selectUsersByOpenIdOrType(String wxOpenId, Integer userType) {
        Map<String, Object> params = new HashMap<>();
        params.put("wxOpenId", wxOpenId);
        params.put("userType", userType);
        return selectList("selectUsersByOpenIdOrType", params);
    }
}
