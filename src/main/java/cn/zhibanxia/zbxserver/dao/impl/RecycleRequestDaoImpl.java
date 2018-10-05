package cn.zhibanxia.zbxserver.dao.impl;

import cn.zhibanxia.zbxserver.bo.ListRecycleRequestBo;
import cn.zhibanxia.zbxserver.dao.BaseDao;
import cn.zhibanxia.zbxserver.dao.RecycleRequestDao;
import cn.zhibanxia.zbxserver.entity.RecycleRequestEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzy on  2018/10/01 10:16
 */
@Component
public class RecycleRequestDaoImpl extends BaseDao implements RecycleRequestDao {
    @Override
    public Long insert(RecycleRequestEntity recycleRequestEntity) {
        insert("insert", recycleRequestEntity);
        return recycleRequestEntity.getId();
    }

    @Override
    public RecycleRequestEntity findById(Long id) {
        return selectOne("findById", id);
    }

    @Override
    public boolean confirmRecycle(Long id, Long recycleUserId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("recycleUserId", recycleUserId);
        params.put("resStatus", RecycleRequestEntity.RES_STATUS_CONFIRM);
        int ret = update("confirmRecycle", params);
        return ret == 1;
    }

    @Override
    public boolean completeRecycle(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("resStatus", RecycleRequestEntity.RES_STATUS_COMPLETE);
        int ret = update("completeRecycle", params);
        return ret == 1;
    }

    @Override
    public boolean update(RecycleRequestEntity recycleRequestEntity) {
        int ret = update("update", recycleRequestEntity);
        return ret == 1;
    }

    @Override
    public List<RecycleRequestEntity> list(ListRecycleRequestBo listRecycleRequestBo) {
        return selectList("list", listRecycleRequestBo);
    }

    @Override
    public Integer count(ListRecycleRequestBo listRecycleRequestBo) {
        return selectOne("count", listRecycleRequestBo);
    }

    @Override
    public boolean delete(Long id) {
        int ret = update("delete", id);
        return ret == 1;
    }
}
