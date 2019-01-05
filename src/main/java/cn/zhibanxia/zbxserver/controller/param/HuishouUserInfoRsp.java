package cn.zhibanxia.zbxserver.controller.param;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 回收人员用户信息
 * Created by zzy on  2018/10/03 21:25
 */
public class HuishouUserInfoRsp implements Serializable {
    private static final long serialVersionUID = -6155964256537009077L;
    private Long uid;
    /**
     * 用户状态
     */
    private Integer status;
    /**
     * 微信昵称
     */
    private String wxNickName;
    /**
     * 微信头像
     */
    private String wxLogo;
    /**
     * 手机号码
     */
    private String mobilePhone;

    /**
     * 审核头像
     */
    private String verifyLogo;

    /**
     * 默认住址
     */
    private Addr defaultAddr;

    /**
     * 关注的小区地址列表
     */
    private List<Addr> focusAddrList;


    /**
     * 是否微信通知，默认通知
     */
    private Integer wxNotifyFlag;

    /**
     * 是否语音通知，默认不通知
     */
    private Integer voiceNotifyFlag;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getWxNickName() {
        return wxNickName;
    }

    public void setWxNickName(String wxNickName) {
        this.wxNickName = wxNickName;
    }

    public String getWxLogo() {
        return wxLogo;
    }

    public void setWxLogo(String wxLogo) {
        this.wxLogo = wxLogo;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getVerifyLogo() {
        return verifyLogo;
    }

    public void setVerifyLogo(String verifyLogo) {
        this.verifyLogo = verifyLogo;
    }

    public Addr getDefaultAddr() {
        return defaultAddr;
    }

    public void setDefaultAddr(Addr defaultAddr) {
        this.defaultAddr = defaultAddr;
    }

    public List<Addr> getFocusAddrList() {
        return focusAddrList;
    }

    public void setFocusAddrList(List<Addr> focusAddrList) {
        this.focusAddrList = focusAddrList;
    }

    public Integer getWxNotifyFlag() {
        return wxNotifyFlag;
    }

    public void setWxNotifyFlag(Integer wxNotifyFlag) {
        this.wxNotifyFlag = wxNotifyFlag;
    }

    public Integer getVoiceNotifyFlag() {
        return voiceNotifyFlag;
    }

    public void setVoiceNotifyFlag(Integer voiceNotifyFlag) {
        this.voiceNotifyFlag = voiceNotifyFlag;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
