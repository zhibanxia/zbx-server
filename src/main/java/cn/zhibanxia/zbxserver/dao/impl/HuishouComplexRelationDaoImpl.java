package cn.zhibanxia.zbxserver.dao.impl;

import cn.zhibanxia.zbxserver.dao.BaseDao;
import cn.zhibanxia.zbxserver.dao.HuishouComplexRelationDao;
import cn.zhibanxia.zbxserver.entity.HuishouComplexRelationEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zzy on  2019/04/06 14:39
 */
@Service
public class HuishouComplexRelationDaoImpl extends BaseDao implements HuishouComplexRelationDao {
    @Override
    public List<HuishouComplexRelationEntity> findByComplexId(Long complexId) {
        return selectList("findByComplexId", complexId);
    }
}
