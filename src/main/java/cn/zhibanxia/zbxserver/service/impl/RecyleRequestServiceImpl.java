package cn.zhibanxia.zbxserver.service.impl;

import cn.zhibanxia.zbxserver.dao.RecyleRequestDao;
import cn.zhibanxia.zbxserver.entity.RecyleRequestEntity;
import cn.zhibanxia.zbxserver.service.RecyleRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zzy on  2018/10/04 22:16
 */
@Service
public class RecyleRequestServiceImpl implements RecyleRequestService {
    @Autowired
    private RecyleRequestDao recyleRequestDao;

    @Override
    public List<RecyleRequestEntity> list(ListReq listReq) {
        return recyleRequestDao.list(listReq);
    }

    @Override
    public Integer count(ListReq listReq) {
        return recyleRequestDao.count(listReq);
    }
}
