package cn.zhibanxia.zbxserver.util;

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

import java.util.Map;

/**
 * Created by zzy on  2019/03/26 23:50
 */
public class SmsService {
    public void sendSms(String phone, String templateCode, String extCode, Map<String, String> templateParamMap) {

    }
}
