package cn.zhibanxia.zbxserver.dao;

import cn.zhibanxia.zbxserver.entity.HuishouComplexRelationEntity;

import java.util.List;

/**
 * Created by zzy on  2019/04/06 14:37
 */
public interface HuishouComplexRelationDao {
    /**
     * 根据小区id查询回收人员列表，最多返回3个
     *
     * @param complexId
     * @return
     */
    List<HuishouComplexRelationEntity> findByComplexId(Long complexId);
}
