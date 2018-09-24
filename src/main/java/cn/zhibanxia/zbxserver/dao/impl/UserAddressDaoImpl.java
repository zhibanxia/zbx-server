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
}
