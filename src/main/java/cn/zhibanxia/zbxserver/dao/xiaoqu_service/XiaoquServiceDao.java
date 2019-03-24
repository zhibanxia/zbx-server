package cn.zhibanxia.zbxserver.dao.xiaoqu_service;

import cn.zhibanxia.zbxserver.entity.XiaoquServiceInfoEntity;

import java.util.List;

/**
 * Created by zzy on  2019/03/23 16:15
 */
public interface XiaoquServiceDao {
    /**
     * 新增
     *
     * @param entity
     * @return
     */
    Long insert(XiaoquServiceInfoEntity entity);

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    int update(XiaoquServiceInfoEntity entity);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 分页查询
     *
     * @param type
     * @param page
     * @param size
     * @return
     */
    List<XiaoquServiceInfoEntity> findList(Integer type, Integer page, Integer size);

    /**
     * 根据主键id查询
     *
     * @param id
     * @return
     */
    XiaoquServiceInfoEntity findById(Long id);
}
