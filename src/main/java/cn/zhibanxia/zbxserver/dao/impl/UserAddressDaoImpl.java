package cn.zhibanxia.zbxserver.dao.impl;

import cn.zhibanxia.zbxserver.dao.BaseDao;
import cn.zhibanxia.zbxserver.dao.UserAddressDao;
import cn.zhibanxia.zbxserver.entity.UserAddressEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzy on  2018/09/24 23:43
 */
@Component
public class UserAddressDaoImpl extends BaseDao implements UserAddressDao {
    @Override
    public Long insert(UserAddressEntity userAddressEntity) {
        insert("insert", userAddressEntity);
        return userAddressEntity.getId();
    }

    @Override
    public List<UserAddressEntity> findAddrs(Long userId, int bizType) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("bizType", bizType);
        return selectList("findAddrs", params);
    }

    @Override
    public int countAddr(Long userId, int bizType) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("bizType", bizType);
        return selectOne("countAddr", params);
    }

    @Override
    public boolean batchInsert(List<UserAddressEntity> userAddressEntityList) {
        int ret = insert("batchInsert", userAddressEntityList);
        return ret == userAddressEntityList.size();
    }

    @Override
    public boolean update(UserAddressEntity userAddressEntity) {
        int ret = update("update", userAddressEntity);
        return ret == 1;
    }

    @Override
    public List<UserAddressEntity> batchFind(List<Long> ids) {
        return selectList("batchFind", ids);
    }
}
