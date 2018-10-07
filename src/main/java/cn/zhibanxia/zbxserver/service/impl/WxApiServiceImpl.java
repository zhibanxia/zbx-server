package cn.zhibanxia.zbxserver.service.impl;

import cn.zhibanxia.zbxserver.bo.WxUserAuthBo;
import cn.zhibanxia.zbxserver.bo.WxUserInfoBo;
import cn.zhibanxia.zbxserver.config.WxPropConfig;
import cn.zhibanxia.zbxserver.constant.ErrorCode;
import cn.zhibanxia.zbxserver.exception.BizException;
import cn.zhibanxia.zbxserver.service.WxApiService;
import cn.zhibanxia.zbxserver.util.HttpClientUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * Created by zzy on  2018/10/02 11:08
 */
@Service
public class WxApiServiceImpl implements WxApiService {
    private static Logger logger = LoggerFactory.getLogger(WxApiServiceImpl.class);

    @Autowired
    private WxPropConfig wxPropConfig;

    @Override
    public WxUserAuthBo userAuth(String code) throws BizException {
        String url = MessageFormat.format(wxPropConfig.getUserAuthUrl(), wxPropConfig.getAppId(), wxPropConfig.getSecret(), code);
        String body = HttpClientUtil.get(url);
        JSONObject jsonObject;
        try {
            jsonObject = JSONObject.parseObject(body);
        } catch (Exception e) {
            logger.warn("", e);
            throw new BizException(ErrorCode.CODE_JSON_PASER_ERROR, e);
        }
        WxUserAuthBo wxUserAuthBo = new WxUserAuthBo();
        wxUserAuthBo.setAccessToken(jsonObject.getString("access_token"));
        wxUserAuthBo.setOpenId(jsonObject.getString("openid"));
        wxUserAuthBo.setExpiresIn(jsonObject.getInteger("expires_in"));
        wxUserAuthBo.setRefreshToken(jsonObject.getString("refresh_token"));
        return wxUserAuthBo;
    }

    @Override
    public WxUserInfoBo getUserInfo(String accessToken, String openId) throws BizException {
        String url = MessageFormat.format(wxPropConfig.getUserInfoUrl(), accessToken, openId);
        String body = HttpClientUtil.get(url);
        JSONObject jsonObject;
        try {
            jsonObject = JSONObject.parseObject(body);
        } catch (Exception e) {
            logger.warn("", e);
            throw new BizException(ErrorCode.CODE_JSON_PASER_ERROR, e);
        }
        WxUserInfoBo wxUserInfoBo = new WxUserInfoBo();
        wxUserInfoBo.setOpenId(jsonObject.getString("openid"));
        wxUserInfoBo.setNickName(jsonObject.getString("nickname"));
        wxUserInfoBo.setSex(jsonObject.getInteger("sex"));
        wxUserInfoBo.setHeadimgurl(jsonObject.getString("headimgurl"));

        wxUserInfoBo.setCountry(jsonObject.getString("country"));
        wxUserInfoBo.setProvince(jsonObject.getString("province"));
        wxUserInfoBo.setCity(jsonObject.getString("city"));
        return wxUserInfoBo;
    }
}
