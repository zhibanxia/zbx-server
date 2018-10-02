package cn.zhibanxia.zbxserver.dao.impl;

import cn.zhibanxia.zbxserver.dao.BaseDao;
import cn.zhibanxia.zbxserver.dao.RecyleRequestDao;
import cn.zhibanxia.zbxserver.entity.RecyleRequestEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzy on  2018/10/01 10:16
 */
@Component
public class RecyleRequestDaoImpl extends BaseDao implements RecyleRequestDao {
    @Override
    public Long insert(RecyleRequestEntity recyleRequestEntity) {
        getSqlSessionTemplate().insert("insert", recyleRequestEntity);
        return recyleRequestEntity.getId();
    }

    @Override
    public RecyleRequestEntity findById(Long id) {
        return getSqlSessionTemplate().selectOne("findById", id);
    }

    @Override
    public boolean confirmRecyle(Long id, Long recyleUserId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("recyleUserId", recyleUserId);
        params.put("resStatus", RecyleRequestEntity.RES_STATUS_CONFIRM);
        int ret = getSqlSessionTemplate().update("confirmRecyle", params);
        return ret == 1;
    }

    @Override
    public boolean completeRecyle(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("resStatus", RecyleRequestEntity.RES_STATUS_COMPLETE);
        int ret = getSqlSessionTemplate().update("completeRecyle", params);
        return ret == 1;
    }

    @Override
    public boolean update(RecyleRequestEntity recyleRequestEntity) {
        int ret = getSqlSessionTemplate().update("completeRecyle", recyleRequestEntity);
        return ret == 1;
    }
}
