package cn.zhibanxia.zbxserver.service;

import java.util.Map;

/**
 * Created by zzy on  2019/04/06 13:03
 */
public interface SmsService {
    /**
     * 发送回收通知短信，回收员可用看到
     *
     * @param phone
     * @param templateCode
     * @param extCode
     * @param templateParamMap
     */
    void sendHuishouNotifySms(String phone, String templateCode, String extCode, Map<String, String> templateParamMap);
}
