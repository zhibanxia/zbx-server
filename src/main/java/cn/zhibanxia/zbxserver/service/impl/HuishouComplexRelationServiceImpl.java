package cn.zhibanxia.zbxserver.service.impl;

import cn.zhibanxia.zbxserver.dao.HuishouComplexRelationDao;
import cn.zhibanxia.zbxserver.entity.HuishouComplexRelationEntity;
import cn.zhibanxia.zbxserver.service.HuishouComplexRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zzy on  2019/04/06 14:41
 */
@Service
public class HuishouComplexRelationServiceImpl implements HuishouComplexRelationService {
    @Autowired
    private HuishouComplexRelationDao huishouComplexRelationDao;

    @Override
    public List<HuishouComplexRelationEntity> findByComplexId(Long complexId) {
        return huishouComplexRelationDao.findByComplexId(complexId);
    }
}
