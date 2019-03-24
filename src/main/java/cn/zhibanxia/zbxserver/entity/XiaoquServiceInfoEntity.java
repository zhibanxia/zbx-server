package cn.zhibanxia.zbxserver.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zzy on  2019/03/10 17:14
 */
public class XiaoquServiceInfoEntity implements Serializable {
    private static final long serialVersionUID = -8953823100841319049L;
    /**
     * 主键id
     */
    private Long id;

    /**
     * 服务类别：
     * 1：回收；
     * 2：家政；
     * 3：团购；
     * 4：其他
     */
    private Integer type;

    /**
     * 服务区域，到区县一级，多个以逗号分隔，all表示全部
     */
    private String serviceAreas;

    /**
     * 头像图片
     */
    private String iconImg;

    /**
     * 详情多图，逗号分隔
     */
    private String detailImgs;

    /**
     * 服务描述
     */
    private String serviceDesc;

    /**
     * 联系人名称
     */
    private String contactName;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 服务星值，除以10为显示值
     */
    private Integer servStarValue;

    /**
     * 删除状态
     */
    private Boolean deleted;

    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改时间
     */
    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getServiceAreas() {
        return serviceAreas;
    }

    public void setServiceAreas(String serviceAreas) {
        this.serviceAreas = serviceAreas;
    }

    public String getIconImg() {
        return iconImg;
    }

    public void setIconImg(String iconImg) {
        this.iconImg = iconImg;
    }

    public String getDetailImgs() {
        return detailImgs;
    }

    public void setDetailImgs(String detailImgs) {
        this.detailImgs = detailImgs;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Integer getServStarValue() {
        return servStarValue;
    }

    public void setServStarValue(Integer servStarValue) {
        this.servStarValue = servStarValue;
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
