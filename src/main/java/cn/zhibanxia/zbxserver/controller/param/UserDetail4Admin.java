package cn.zhibanxia.zbxserver.controller.param;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zzy on  2018/10/08 22:47
 */
public class UserDetail4Admin implements Serializable {
    private static final long serialVersionUID = 2637695988415981397L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 用户类型：1.业主；2.回收人员；3.管理员
     */
    private Integer userType = 0;
    /**
     * 微信openId
     */
    private String wxOpenId;
    /**
     * 微信昵称
     */
    private String wxNickName;
    /**
     * 微信头像
     */
    private String wxLogo;
    /**
     * 用户状态：
     * 1.正常
     * 2.待提交审核
     * 3.审核不通过，请重新提交审核
     * 4.审核中
     * 5.禁用
     * 6.注销
     */
    private Integer userStatus = 0;
    /**
     * 手机号码
     */
    private String mobilePhone;
    /**
     * 回收人员提交的审核头像
     */
    private String verifyLogo;
    private Date gmtCreate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
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

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
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

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
