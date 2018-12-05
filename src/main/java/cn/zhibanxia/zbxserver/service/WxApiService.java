package cn.zhibanxia.zbxserver.service;

import cn.zhibanxia.zbxserver.bo.WxTemplateMsgReqBo;
import cn.zhibanxia.zbxserver.bo.WxUserAuthBo;
import cn.zhibanxia.zbxserver.bo.WxUserInfoBo;
import cn.zhibanxia.zbxserver.exception.BizException;

/**
 * Created by zzy on  2018/10/02 10:09
 */
public interface WxApiService {
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


    /**
     * 获取access_token
     *
     * @return
     * @throws BizException
     */
    String getAccessToken() throws BizException;

    /**
     * @param wxTemplateMsgReqBo
     * @throws BizException
     */
    void sendTemplateMsg(WxTemplateMsgReqBo wxTemplateMsgReqBo) throws BizException;
}
