package cn.zhibanxia.zbxserver.service.impl;

import cn.zhibanxia.zbxserver.bo.ListRecycleRequestBo;
import cn.zhibanxia.zbxserver.dao.RecycleRequestDao;
import cn.zhibanxia.zbxserver.entity.RecycleRequestEntity;
import cn.zhibanxia.zbxserver.service.RecycleRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zzy on  2018/10/04 22:16
 */
@Service
public class RecycleRequestServiceImpl implements RecycleRequestService {
    @Autowired
    private RecycleRequestDao recycleRequestDao;

    @Override
    public Long create(RecycleRequestEntity recycleRequestEntity) {
        return recycleRequestDao.insert(recycleRequestEntity);
    }

    @Override
    public List<RecycleRequestEntity> list(ListRecycleRequestBo listRecycleRequestBo) {
        return recycleRequestDao.list(listRecycleRequestBo);
    }

    @Override
    public Integer count(ListRecycleRequestBo listRecycleRequestBo) {
        return recycleRequestDao.count(listRecycleRequestBo);
    }

    @Override
    public RecycleRequestEntity find(Long id) {
        return recycleRequestDao.findById(id);
    }

    @Override
    public boolean confirmRecycleRequest(Long id, Long recycleUserId) {
        return recycleRequestDao.confirmRecycle(id, recycleUserId);
    }

    @Override
    public boolean completeRecycleRequest(Long id) {
        return recycleRequestDao.completeRecycle(id);
    }

    @Override
    public boolean delete(Long id) {
        return recycleRequestDao.delete(id);
    }

    @Override
    public boolean update(RecycleRequestEntity entity) {
        return recycleRequestDao.update(entity);
    }
}
