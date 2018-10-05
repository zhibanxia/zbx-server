package cn.zhibanxia.zbxserver.bo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zzy on  2018/10/05 21:31
 */
public class ListRecycleRequestBo implements Serializable {
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
