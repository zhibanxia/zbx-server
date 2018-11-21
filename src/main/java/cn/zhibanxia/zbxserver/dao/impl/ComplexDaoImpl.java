package cn.zhibanxia.zbxserver.dao.impl;

import cn.zhibanxia.zbxserver.dao.BaseDao;
import cn.zhibanxia.zbxserver.dao.ComplexDao;
import cn.zhibanxia.zbxserver.entity.ComplexEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zzy on  2018/11/18 17:49
 */
@Component
public class ComplexDaoImpl extends BaseDao implements ComplexDao {
    @Override
    public List<ComplexEntity> searchComplex(ComplexEntity complexEntity) {
        return selectList("searchComplex", complexEntity);
    }

    @Override
    public ComplexEntity find(Long id) {
        return selectOne("find", id);
    }

    @Override
    public List<ComplexEntity> findByIds(List<Long> ids) {
        return selectList("findByIds", ids);
    }
}
