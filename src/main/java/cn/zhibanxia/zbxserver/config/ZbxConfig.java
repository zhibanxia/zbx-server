package cn.zhibanxia.zbxserver.config;

import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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

    @Value("${zbx.admin.openIds}")
    private String adminOpenIds;

    @Value("${zbx.push.templateId}")
    private String templateId;

    private final Splitter splitter = Splitter.on(',');

    private Set<String> adminOpenIdSet;

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

    public String getTemplateId() {
        return templateId;
    }

    public Set<String> getAdminOpenIdSet() {
        if (adminOpenIdSet != null) {
            return adminOpenIdSet;
        }
        if (StringUtils.isBlank(adminOpenIds)) {
            adminOpenIdSet = Collections.emptySet();
        } else {
            adminOpenIdSet = new HashSet<>(splitter.omitEmptyStrings().trimResults().splitToList(adminOpenIds));
        }
        return adminOpenIdSet;
    }


}
