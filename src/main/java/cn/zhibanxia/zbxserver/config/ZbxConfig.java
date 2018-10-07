package cn.zhibanxia.zbxserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by zzy on  2018/10/02 21:44
 */
@Component
public class ZbxConfig {
    @Value("${zbx.encrypt.key}")
    private String encryptKey;

    @Value("${zbx.service.domain}")
    private String zbxServiceDomain;

    @Value("${zbx.cookie.domain}")
    private String cookieDomain;

    @Value("${zbx.huishou.max.focus.addrNum:3}")
    private int maxFocusAddrNum;

    public String getEncryptKey() {
        return encryptKey;
    }

    public String getZbxServiceDomain() {
        return zbxServiceDomain;
    }

    public String getCookieDomain() {
        return cookieDomain;
    }

    public int getMaxFocusAddrNum() {
        return maxFocusAddrNum;
    }
}
