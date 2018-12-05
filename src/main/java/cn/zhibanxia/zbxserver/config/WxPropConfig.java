package cn.zhibanxia.zbxserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by zzy on  2018/10/02 11:11
 */
@Component
@ConfigurationProperties(prefix = "wx.config")
public class WxPropConfig {
    private String appId;
    private String secret;
    private String authRedirectUrl;
    private String userAuthUrl;
    private String userInfoUrl;
    private String accessTokenUrl;
    private String sendTemplateUrl;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getAuthRedirectUrl() {
        return authRedirectUrl;
    }

    public void setAuthRedirectUrl(String authRedirectUrl) {
        this.authRedirectUrl = authRedirectUrl;
    }

    public String getUserAuthUrl() {
        return userAuthUrl;
    }

    public void setUserAuthUrl(String userAuthUrl) {
        this.userAuthUrl = userAuthUrl;
    }

    public String getUserInfoUrl() {
        return userInfoUrl;
    }

    public void setUserInfoUrl(String userInfoUrl) {
        this.userInfoUrl = userInfoUrl;
    }

    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }

    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    public String getSendTemplateUrl() {
        return sendTemplateUrl;
    }

    public void setSendTemplateUrl(String sendTemplateUrl) {
        this.sendTemplateUrl = sendTemplateUrl;
    }
}
