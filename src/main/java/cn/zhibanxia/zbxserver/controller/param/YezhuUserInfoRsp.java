package cn.zhibanxia.zbxserver.controller.param;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Created by zzy on  2018/10/03 18:18
 */
public class YezhuUserInfoRsp implements Serializable {
    private static final long serialVersionUID = 8418000109345164414L;
    private Long uid;
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
     * 默认住址
     */
    private Addr defaultAddr;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
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

    public Addr getDefaultAddr() {
        return defaultAddr;
    }

    public void setDefaultAddr(Addr defaultAddr) {
        this.defaultAddr = defaultAddr;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
