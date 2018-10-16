package cn.zhibanxia.zbxserver.entity;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * Created by zzy on  2018/09/23 11:35
 */
public class UserEntity {


    /**
     * 用户类型：业主
     */
    public static final int USER_TYPE_YEZHU = 1;
    /**
     * 用户类型：回收人员
     */
    public static final int USER_TYPE_HUISHOU = 2;
    /**
     * 管理员
     */
    public static final int USER_TYPE_ADMIN = 3;

    /**
     * 正常
     */
    public static final int USER_STATUS_NORMAL = 1;
    /**
     * 回收人员：待激活
     */
    public static final int USER_STATUS_NEED_ACTIVE = 2;
    /**
     * 回收人员：审核不通过
     */
    public static final int USER_STATUS_NOT_PERMIT = 3;

    /**
     * 回收人员：审核中
     */
    public static final int USER_STATUS_PERMIT_PROCESS = 4;
    /**
     * 回收人员：禁用
     */
    public static final int USER_STATUS_FORBIDDEN = 5;
    /**
     * 注销
     */
    public static final int USER_STATUS_OFF = 6;

    /**
     * 用户状态集
     */
    public static final ImmutableSet<Integer> STATUS_SET = ImmutableSet.of(USER_STATUS_NORMAL, USER_STATUS_NEED_ACTIVE, USER_STATUS_NOT_PERMIT, USER_STATUS_PERMIT_PROCESS, USER_STATUS_FORBIDDEN, USER_STATUS_OFF);
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
     * 微信id
     */
    private String wxId;
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

    /**
     * 审核备注，用于审核拒绝时的原因填写
     */
    private String verifyRemark;
    private Date gmtCreate;
    private Date gmtModified;

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

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
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

    public String getVerifyRemark() {
        return verifyRemark;
    }

    public void setVerifyRemark(String verifyRemark) {
        this.verifyRemark = verifyRemark;
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
