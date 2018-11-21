package cn.zhibanxia.zbxserver.dao;

import cn.zhibanxia.zbxserver.entity.ComplexEntity;

import java.util.List;

/**
 * Created by zzy on  2018/11/18 17:47
 */
public interface ComplexDao {
    /**
     * 根据小区名称搜索
     *
     * @param complexEntity 省市区街道编码必填，小区名称为搜索输入值
     * @return
     */
    List<ComplexEntity> searchComplex(ComplexEntity complexEntity);

    /**
     * 根据id查询小区信息
     *
     * @param id 小区库id
     * @return
     */
    ComplexEntity find(Long id);

    /**
     * 根据id查询小区信息
     *
     * @param ids 小区库id列表
     * @return
     */
    List<ComplexEntity> findByIds(List<Long> ids);
}
