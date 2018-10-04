package cn.zhibanxia.zbxserver.controller.param;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * 回收请求列表数据
 * Created by zzy on  2018/10/04 21:42
 */
public class RecyleRequestVo implements Serializable {
    private static final long serialVersionUID = -8492753300547953307L;
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
    private boolean takeGarbageFlag;
    /**
     * 是否免费回收
     */
    private boolean freeTakeFlag;
    /**
     * 备注说明
     */
    private String resNote;
    /**
     * 上门时间段：起始
     */
    private String doorServStartTime;
    /**
     * 上门时间段：截止
     */
    private String doorServEndTime;
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
     * 业主手机号码，用于回收人员联系使用
     */
    private String mobilePhone;

    /**
     * 发布时间
     */
    private String publishTime;
    /**
     * 确认回收时间
     */
    private String comfirmRecyleTime;
    /**
     * 完成回收时间
     */
    private String completeRecyleTime;

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

    public boolean isTakeGarbageFlag() {
        return takeGarbageFlag;
    }

    public void setTakeGarbageFlag(boolean takeGarbageFlag) {
        this.takeGarbageFlag = takeGarbageFlag;
    }

    public boolean isFreeTakeFlag() {
        return freeTakeFlag;
    }

    public void setFreeTakeFlag(boolean freeTakeFlag) {
        this.freeTakeFlag = freeTakeFlag;
    }

    public String getResNote() {
        return resNote;
    }

    public void setResNote(String resNote) {
        this.resNote = resNote;
    }

    public String getDoorServStartTime() {
        return doorServStartTime;
    }

    public void setDoorServStartTime(String doorServStartTime) {
        this.doorServStartTime = doorServStartTime;
    }

    public String getDoorServEndTime() {
        return doorServEndTime;
    }

    public void setDoorServEndTime(String doorServEndTime) {
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

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getComfirmRecyleTime() {
        return comfirmRecyleTime;
    }

    public void setComfirmRecyleTime(String comfirmRecyleTime) {
        this.comfirmRecyleTime = comfirmRecyleTime;
    }

    public String getCompleteRecyleTime() {
        return completeRecyleTime;
    }

    public void setCompleteRecyleTime(String completeRecyleTime) {
        this.completeRecyleTime = completeRecyleTime;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
