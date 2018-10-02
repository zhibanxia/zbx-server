package cn.zhibanxia.zbxserver.service;

import cn.zhibanxia.zbxserver.bo.WxUserAuthBo;
import cn.zhibanxia.zbxserver.bo.WxUserInfoBo;
import cn.zhibanxia.zbxserver.exception.BizException;

/**
 * Created by zzy on  2018/10/02 10:09
 */
public interface WxApiService {

    /**
     * 获取授权跳转页
     */
    String AUTH_REDIRECT_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={0}&redirect_uri={1}&response_type=code&scope=snsapi_userinfo&state={2}#wechat_redirect";
    /**
     * 获取openId和accessToken
     */
    String USER_AUTH_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&code={2}&grant_type=authorization_code";

    /**
     * 获取用户信息
     */
    String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token={0}&openid={1}&lang=zh_CN";

    /**
     * 用户授权，根据code换取accessToken和openId
     *
     * @param code
     * @return
     * @see <link>https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842</link>
     */
    WxUserAuthBo userAuth(String code) throws BizException;

    /**
     * 根据accessToken和openId获取用户信息
     *
     * @param accessToken
     * @param openId
     * @return
     */
    WxUserInfoBo getUserInfo(String accessToken, String openId) throws BizException;
}
