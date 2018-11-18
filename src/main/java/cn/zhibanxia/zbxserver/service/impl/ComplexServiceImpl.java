package cn.zhibanxia.zbxserver.service.impl;

import cn.zhibanxia.zbxserver.dao.ComplexDao;
import cn.zhibanxia.zbxserver.entity.ComplexEntity;
import cn.zhibanxia.zbxserver.service.ComplexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zzy on  2018/11/18 17:52
 */
@Service
public class ComplexServiceImpl implements ComplexService {
    @Autowired
    private ComplexDao complexDao;

    @Override
    public List<ComplexEntity> searchComplex(ComplexEntity complexEntity) {
        // 目前没有4级地址信息，填-1
        complexEntity.setSubdistrictId("-1");
        return complexDao.searchComplex(complexEntity);
    }

    @Override
    public ComplexEntity findById(List<Long> ids) {
        return complexDao.findById(ids);
    }
}
