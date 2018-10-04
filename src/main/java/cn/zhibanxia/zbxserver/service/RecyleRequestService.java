package cn.zhibanxia.zbxserver.service;

import cn.zhibanxia.zbxserver.entity.RecyleRequestEntity;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by zzy on  2018/10/04 21:57
 */
public interface RecyleRequestService {
    /**
     * 分页查询列表
     *
     * @param listReq 分页请求
     * @return
     */
    List<RecyleRequestEntity> list(ListReq listReq);

    /**
     * 根据条件查询总数
     *
     * @return
     */
    Integer count(ListReq listReq);

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
         * 发布人id
         */
        private Long createUserId;
        /**
         * 确认回收人id
         */
        private Long recyleUserId;
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

        public Long getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(Long createUserId) {
            this.createUserId = createUserId;
        }

        public Long getRecyleUserId() {
            return recyleUserId;
        }

        public void setRecyleUserId(Long recyleUserId) {
            this.recyleUserId = recyleUserId;
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
