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

    public String getEncryptKey() {
        return encryptKey;
    }
}
