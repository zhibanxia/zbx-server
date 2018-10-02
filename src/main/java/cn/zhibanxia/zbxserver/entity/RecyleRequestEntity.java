package cn.zhibanxia.zbxserver.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * Created by zzy on  2018/09/24 15:58
 */
public class RecyleRequestEntity {

    /**
     * 发布
     */
    public static final int RES_STATUS_PUBLISH = 1;
    /**
     * 确认回收
     */
    public static final int RES_STATUS_CONFIRM = 2;
    /**
     * 回收完成
     */
    public static final int RES_STATUS_COMPLETE = 3;
    /**
     * 取消
     */
    public static final int RES_STATUS_CANCEL = 4;

    private Long id;
    /**
     * 发布人id
     */
    private Long createUserId;
    /**
     * 确认回收人id
     */
    private Long recyleUserId;
    /**
     * 资源类型值
     * 1.纸板
     * 2.塑料瓶
     * 3.纸板和塑料瓶
     */
    private Integer resType;
    /**
     * 回收资源状态：
     * 1.已发布
     * 2.已确认待回收
     * 3.已确认已回收
     * 4.取消
     */
    private Integer resStatus;
    /**
     * 回收数量:
     * 1.0-0.5kg 免费回收
     * 2.0.5-1kg
     * 3.1-3kg
     * 4.3-5kg
     * 5.5kg+
     */
    private Integer resAmount;
    /**
     * 废品照片，多张，逗号分隔，最多3张
     */
    private String resImages;
    /**
     * 是否帮忙带垃圾
     */
    private Boolean takeGarbageFlag;
    /**
     * 是否免费回收
     */
    private Boolean freeTakeFlag;
    /**
     * 备注说明
     */
    private String resNote;
    /**
     * 上门时间段：起始
     */
    private Date doorServStartTime;
    /**
     * 上门时间段：截止
     */
    private Date doorServEndTime;
    /**
     * 省id
     */
    private String provinceId;
    /**
     * 市id
     */
    private String cityId;
    /**
     * 区id
     */
    private String areaId;
    /**
     * 街道id
     */
    private String subDistrictId;
    /**
     * 详细地址
     */
    private String areaDetail;
    /**
     * 发布时间
     */
    private Date publishTime;
    /**
     * 确认回收时间
     */
    private Date comfirmRecyleTime;
    /**
     * 完成回收时间
     */
    private Date completeRecyleTime;
    private Date gmtCreate;
    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getResType() {
        return resType;
    }

    public void setResType(Integer resType) {
        this.resType = resType;
    }

    public Integer getResStatus() {
        return resStatus;
    }

    public void setResStatus(Integer resStatus) {
        this.resStatus = resStatus;
    }

    public Integer getResAmount() {
        return resAmount;
    }

    public void setResAmount(Integer resAmount) {
        this.resAmount = resAmount;
    }

    public String getResImages() {
        return resImages;
    }

    public void setResImages(String resImages) {
        this.resImages = resImages;
    }

    public boolean isFreeTakeFlag() {
        return freeTakeFlag;
    }

    public void setFreeTakeFlag(boolean freeTakeFlag) {
        this.freeTakeFlag = freeTakeFlag;
    }

    public Date getCompleteRecyleTime() {
        return completeRecyleTime;
    }

    public void setCompleteRecyleTime(Date completeRecyleTime) {
        this.completeRecyleTime = completeRecyleTime;
    }

    public boolean isTakeGarbageFlag() {
        return takeGarbageFlag;
    }

    public void setTakeGarbageFlag(boolean takeGarbageFlag) {
        this.takeGarbageFlag = takeGarbageFlag;
    }

    public String getResNote() {
        return resNote;
    }

    public void setResNote(String resNote) {
        this.resNote = resNote;
    }

    public Date getDoorServStartTime() {
        return doorServStartTime;
    }

    public void setDoorServStartTime(Date doorServStartTime) {
        this.doorServStartTime = doorServStartTime;
    }

    public Date getDoorServEndTime() {
        return doorServEndTime;
    }

    public void setDoorServEndTime(Date doorServEndTime) {
        this.doorServEndTime = doorServEndTime;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getSubDistrictId() {
        return subDistrictId;
    }

    public void setSubDistrictId(String subDistrictId) {
        this.subDistrictId = subDistrictId;
    }

    public String getAreaDetail() {
        return areaDetail;
    }

    public void setAreaDetail(String areaDetail) {
        this.areaDetail = areaDetail;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date getComfirmRecyleTime() {
        return comfirmRecyleTime;
    }

    public void setComfirmRecyleTime(Date comfirmRecyleTime) {
        this.comfirmRecyleTime = comfirmRecyleTime;
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
