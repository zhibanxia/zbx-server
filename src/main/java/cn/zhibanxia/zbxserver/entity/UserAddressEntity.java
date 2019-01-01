package cn.zhibanxia.zbxserver.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * Created by zzy on  2018/09/23 11:50
 */
public class UserAddressEntity {
    /**
     * 业主默认地址
     */
    public static final int BIZ_TYPE_YEZHU = 1;
    /**
     * 回收人员默认住处
     */
    public static final int BIZ_TYPE_HUISHOU = 2;
    /**
     * 回收人员关注的小区地址
     */
    public static final int BIZ_TYPE_HUISHOU_FOCUS = 3;

    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 地址类型：
     * 1.业主常用住址；
     * 2.回收人员住址；
     * 3.回收人员关注的小区信息
     */
    private Integer bizType = 0;

    /**
     * 小区id
     */
    private Long complexId;
    /**
     * 门牌号
     */
    private String doorInfo;

    /**
     * 是否删除
     */
    private Boolean deleted;
    private Date gmtCreate;
    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getBizType() {
        return bizType;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    public Long getComplexId() {
        return complexId;
    }

    public void setComplexId(Long complexId) {
        this.complexId = complexId;
    }

    public String getDoorInfo() {
        return doorInfo;
    }

    public void setDoorInfo(String doorInfo) {
        this.doorInfo = doorInfo;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
