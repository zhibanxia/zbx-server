package cn.zhibanxia.zbxserver.service.impl;

import cn.zhibanxia.zbxserver.service.SmsService;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by zzy on  2019/04/06 13:04
 */
@Service
public class SmsServiceImpl implements SmsService {

    @Value("${zbx.sms.accessKeyId}")
    private String smsAccessKeyId;
    @Value("${zbx.sms.accessSecret}")
    private String smsAccessSecret;

    @Override
    public void sendHuishouNotifySms(String phone, String templateCode, String extCode, Map<String, String> templateParamMap) {
        DefaultProfile profile = DefaultProfile.getProfile("default", smsAccessKeyId, smsAccessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "纸板侠");
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(templateParamMap));
        request.putQueryParameter("SmsUpExtendCode", extCode);
        request.putQueryParameter("OutId", extCode);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
