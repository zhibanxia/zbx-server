package cn.zhibanxia.zbxserver.controller.param;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 回收请求列表数据
 * Created by zzy on  2018/10/04 21:42
 */
public class RecycleRequestVo implements Serializable {
    private static final long serialVersionUID = -8492753300547953307L;
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
    @NotNull
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
    @NotNull
    private Integer resAmount;
    /**
     * 废品照片，多张，逗号分隔，最多3张
     */
    private String resImages;
    /**
     * 是否帮忙带垃圾
     */
    private Boolean takeGarbageFlag = false;
    /**
     * 是否免费回收
     */
    private Boolean freeTakeFlag = false;
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
     * 回收地址信息
     */
    @NotNull
    private Addr addr;

    /**
     * 业主手机号码，用于回收人员联系使用
     */
    @NotNull
    private String mobilePhone;

    /**
     * 发布时间
     */
    private String publishTime;
    /**
     * 确认回收时间
     */
    private String confirmRecycleTime;
    /**
     * 完成回收时间
     */
    private String completeRecycleTime;

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

    public Addr getAddr() {
        return addr;
    }

    public void setAddr(Addr addr) {
        this.addr = addr;
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

    public String getConfirmRecycleTime() {
        return confirmRecycleTime;
    }

    public void setConfirmRecycleTime(String confirmRecycleTime) {
        this.confirmRecycleTime = confirmRecycleTime;
    }

    public String getCompleteRecycleTime() {
        return completeRecycleTime;
    }

    public void setCompleteRecycleTime(String completeRecycleTime) {
        this.completeRecycleTime = completeRecycleTime;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
