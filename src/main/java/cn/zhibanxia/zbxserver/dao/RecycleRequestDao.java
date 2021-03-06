package cn.zhibanxia.zbxserver.dao;

import cn.zhibanxia.zbxserver.bo.ListRecycleRequestBo;
import cn.zhibanxia.zbxserver.entity.RecycleRequestEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by zzy on  2018/10/01 09:52
 */
public interface RecycleRequestDao {
    /**
     * 新增
     *
     * @param recycleRequestEntity
     * @return
     */
    Long insert(RecycleRequestEntity recycleRequestEntity);

    /**
     * 根据主键id查询
     *
     * @param id
     * @return
     */
    RecycleRequestEntity findById(Long id);

    /**
     * 确认回收，更新回收人员id，更新状态，更新确认回收时间
     *
     * @param id
     * @param recycleUserId 回收人员id
     * @return
     */
    boolean confirmRecycle(Long id, Long recycleUserId);

    /**
     * 完成回收
     *
     * @param id
     * @return
     */
    boolean completeRecycle(Long id);

    /**
     * 更新，用于业主更新回收请求
     *
     * @param recycleRequestEntity
     * @return
     */
    boolean update(RecycleRequestEntity recycleRequestEntity);

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
     * 删除回收请求
     *
     * @param id
     * @return
     */
    boolean delete(Long id);

    /**
     * 查询符合条件的推荐回收订单
     *
     * @param startTime
     * @return
     */
    List<RecycleRequestEntity> selectRecomm(Date startTime);

    /**
     * 回收请求更新为已推荐
     *
     * @param id
     * @return
     */
    boolean updateRecommed(Long id);
}
