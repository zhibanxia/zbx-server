package cn.zhibanxia.zbxserver.bo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Created by zzy on  2018/10/02 10:39
 */
public class WxUserInfoBo implements Serializable {
    private static final long serialVersionUID = -4844238815213393210L;
    private String openId;
    private String nickName;
    /**
     * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    private Integer sex;
    private String province;
    private String city;
    private String country;
    /**
     * 用户头像
     */
    private String headimgurl;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
