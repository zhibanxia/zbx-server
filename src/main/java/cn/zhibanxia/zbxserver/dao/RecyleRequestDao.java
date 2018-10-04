package cn.zhibanxia.zbxserver.dao;

import cn.zhibanxia.zbxserver.entity.RecyleRequestEntity;
import cn.zhibanxia.zbxserver.service.RecyleRequestService;

import java.util.List;

/**
 * Created by zzy on  2018/10/01 09:52
 */
public interface RecyleRequestDao {
    /**
     * 新增
     *
     * @param recyleRequestEntity
     * @return
     */
    Long insert(RecyleRequestEntity recyleRequestEntity);

    /**
     * 根据主键id查询
     *
     * @param id
     * @return
     */
    RecyleRequestEntity findById(Long id);

    /**
     * 确认回收，更新回收人员id，更新状态，更新确认回收时间
     *
     * @param id
     * @param recyleUserId 回收人员id
     * @return
     */
    boolean confirmRecyle(Long id, Long recyleUserId);

    /**
     * 完成回收
     *
     * @param id
     * @return
     */
    boolean completeRecyle(Long id);

    /**
     * 更新，用于业主更新回收请求
     *
     * @param recyleRequestEntity
     * @return
     */
    boolean update(RecyleRequestEntity recyleRequestEntity);

    /**
     * 分页查询列表
     *
     * @param listReq 分页请求
     * @return
     */
    List<RecyleRequestEntity> list(RecyleRequestService.ListReq listReq);

    /**
     * 根据条件查询总数
     *
     * @return
     */
    Integer count(RecyleRequestService.ListReq listReq);
}
