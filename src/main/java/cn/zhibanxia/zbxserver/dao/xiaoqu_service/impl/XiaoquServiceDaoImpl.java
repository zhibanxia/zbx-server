package cn.zhibanxia.zbxserver.dao.xiaoqu_service.impl;

import cn.zhibanxia.zbxserver.dao.BaseDao;
import cn.zhibanxia.zbxserver.dao.xiaoqu_service.XiaoquServiceDao;
import cn.zhibanxia.zbxserver.entity.XiaoquServiceInfoEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzy on  2019/03/23 16:22
 */
@Service
public class XiaoquServiceDaoImpl extends BaseDao implements XiaoquServiceDao {
    @Override
    public Long insert(XiaoquServiceInfoEntity entity) {
        insert("insert", entity);
        return entity.getId();
    }

    @Override
    public int update(XiaoquServiceInfoEntity entity) {
        return update("update", entity);
    }

    @Override
    public int delete(Long id) {
        return update("delete", id);
    }

    @Override
    public List<XiaoquServiceInfoEntity> findList(Integer type, Integer page, Integer size) {
        int pageVal = (page == null || page <= 0) ? 1 : page.intValue();
        int pageSize = (size == null || size <= 0) ? 10 : size.intValue();
        int startPage = (pageVal - 1) * pageSize;
        int endPage = pageVal * pageSize;
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("startPage", startPage);
        params.put("endPage", endPage);
        return selectList("findList", params);
    }

    @Override
    public XiaoquServiceInfoEntity findById(Long id) {
        return selectOne("findById", id);
    }
}
