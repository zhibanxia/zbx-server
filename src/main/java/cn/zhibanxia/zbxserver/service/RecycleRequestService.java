package cn.zhibanxia.zbxserver.service;

import cn.zhibanxia.zbxserver.bo.ListRecycleRequestBo;
import cn.zhibanxia.zbxserver.entity.RecycleRequestEntity;

import java.util.List;

/**
 * Created by zzy on  2018/10/04 21:57
 */
public interface RecycleRequestService {

    /**
     * 发布回收请求
     *
     * @param recycleRequestEntity
     * @return
     */
    Long create(RecycleRequestEntity recycleRequestEntity);

    /**
     * 分页查询列表
     *
     * @param listRecycleRequestBo 分页请求
     * @return
     */
    List<RecycleRequestEntity> list(ListRecycleRequestBo listRecycleRequestBo);

    /**
     * 根据条件查询总数
     *
     * @return
     */
    Integer count(ListRecycleRequestBo listRecycleRequestBo);


    /**
     * 根据主键id查询
     *
     * @param id
     * @return
     */
    RecycleRequestEntity find(Long id);

    /**
     * 确认回收请求
     *
     * @param id
     * @param recycleUserId
     * @return
     */
    boolean confirmRecycleRequest(Long id, Long recycleUserId);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean delete(Long id);

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    boolean update(RecycleRequestEntity entity);
}
