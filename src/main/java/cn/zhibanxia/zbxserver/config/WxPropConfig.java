package cn.zhibanxia.zbxserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by zzy on  2018/10/02 11:11
 */
@ConfigurationProperties(prefix = "wx.config")
public class WxPropConfig {
    private String appId;
    private String secret;

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
}
