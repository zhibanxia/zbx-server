package cn.zhibanxia.zbxserver.entity;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * Created by zzy on  2018/09/24 15:58
 */
public class RecycleRequestEntity {

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

    /**
     * 资源类型，纸板
     */
    public static final int RES_TYPE_ZHIBAN = 1;
    /**
     * 资源类型，塑料瓶
     */
    public static final int RES_TYPE_SULIAOPING = 2;
    /**
     * 资源类型，纸板和塑料瓶
     */
    public static final int RES_TYPE_ZHIBAN_AND_SULIAOPING = 3;

    /**
     * 资源类型
     */
    public static final ImmutableSet<Integer> RES_TYPES = ImmutableSet.of(RES_TYPE_ZHIBAN, RES_TYPE_SULIAOPING, RES_TYPE_ZHIBAN_AND_SULIAOPING);

    /**
     * 资源数量
     */
    public static final ImmutableSet<Integer> RES_AMOUNTS = ImmutableSet.of(1, 2, 3, 4, 5);

    private Long id;
    /**
     * 发布人id
     */
    private Long createUserId;
    /**
     * 确认回收人id
     */
    private Long recycleUserId;
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
     * 是否删除
     */
    private Boolean deleted = false;
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
     * 小区库id
     */
    private Long complexId;

    /**
     * 门牌号信息
     */
    private String doorInfo;
    /**
     * 业主手机号码，用于回收人员联系使用
     */
    private String mobilePhone;

    /**
     * 回收请求是否已经推荐回收人员
     */
    private Boolean hsuRecommFlag;
    /**
     * 发布时间
     */
    private Date publishTime;
    /**
     * 确认回收时间
     */
    private Date confirmRecycleTime;
    /**
     * 完成回收时间
     */
    private Date completeRecycleTime;

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

    public Long getRecycleUserId() {
        return recycleUserId;
    }

    public void setRecycleUserId(Long recycleUserId) {
        this.recycleUserId = recycleUserId;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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

    public Date getCompleteRecycleTime() {
        return completeRecycleTime;
    }

    public void setCompleteRecycleTime(Date completeRecycleTime) {
        this.completeRecycleTime = completeRecycleTime;
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

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date getConfirmRecycleTime() {
        return confirmRecycleTime;
    }

    public void setConfirmRecycleTime(Date confirmRecycleTime) {
        this.confirmRecycleTime = confirmRecycleTime;
    }

    public Boolean getHsuRecommFlag() {
        return hsuRecommFlag;
    }

    public void setHsuRecommFlag(Boolean hsuRecommFlag) {
        this.hsuRecommFlag = hsuRecommFlag;
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
