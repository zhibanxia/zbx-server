package cn.zhibanxia.zbxserver.service;

import cn.zhibanxia.zbxserver.entity.RecycleRequestEntity;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;
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
     * @param listReq 分页请求
     * @return
     */
    List<RecycleRequestEntity> list(ListReq listReq);

    /**
     * 根据条件查询总数
     *
     * @return
     */
    Integer count(ListReq listReq);


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

    /**
     * 查询回收列表请求对象
     */
    class ListReq implements Serializable {
        private static final long serialVersionUID = -5722656789271505028L;
        /**
         * 起始时间
         */
        private Date start;
        /**
         * 结束时间
         */
        private Date end;

        /**
         * 资源状态
         */
        private Integer resStatus;
        /**
         * 是否删除
         */
        private Boolean deleted;
        /**
         * 发布人id
         */
        private Long createUserId;
        /**
         * 确认回收人id
         */
        private Long recycleUserId;
        /**
         * 起始页码
         */
        private int startPage;
        /**
         * 结束页码
         */
        private int endPage;

        public Date getStart() {
            return start;
        }

        public void setStart(Date start) {
            this.start = start;
        }

        public Date getEnd() {
            return end;
        }

        public void setEnd(Date end) {
            this.end = end;
        }

        public Integer getResStatus() {
            return resStatus;
        }

        public void setResStatus(Integer resStatus) {
            this.resStatus = resStatus;
        }

        public Boolean getDeleted() {
            return deleted;
        }

        public void setDeleted(Boolean deleted) {
            this.deleted = deleted;
        }

        public Long getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(Long createUserId) {
            this.createUserId = createUserId;
        }

        public Long getRecycleUserId() {
            return recycleUserId;
        }

        public void setRecycleUserId(Long recycleUserId) {
            this.recycleUserId = recycleUserId;
        }

        public int getStartPage() {
            return startPage;
        }

        public void setStartPage(int startPage) {
            this.startPage = startPage;
        }

        public int getEndPage() {
            return endPage;
        }

        public void setEndPage(int endPage) {
            this.endPage = endPage;
        }

        @Override
        public String toString() {
            return ReflectionToStringBuilder.toString(this);
        }
    }
}
