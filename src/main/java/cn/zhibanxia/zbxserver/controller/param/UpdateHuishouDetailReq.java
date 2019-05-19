package cn.zhibanxia.zbxserver.controller.param;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 更新回收员回收信息
 * Created by zzy on  2019/04/21 23:17
 */
public class UpdateHuishouDetailReq implements Serializable {
    private static final long serialVersionUID = -3819488618080270289L;
    /**
     * 关注的小区地址
     */
    private List<Addr> focusAddrList;

    /**
     * 是否微信通知，默认通知
     */
    private Integer wxNotifyFlag;

    /**
     * 是否语音通知，默认不通知
     */
    private Integer voiceNotifyFlag;

    public List<Addr> getFocusAddrList() {
        return focusAddrList;
    }

    public void setFocusAddrList(List<Addr> focusAddrList) {
        this.focusAddrList = focusAddrList;
    }

    public Integer getWxNotifyFlag() {
        return wxNotifyFlag;
    }

    public void setWxNotifyFlag(Integer wxNotifyFlag) {
        this.wxNotifyFlag = wxNotifyFlag;
    }

    public Integer getVoiceNotifyFlag() {
        return voiceNotifyFlag;
    }

    public void setVoiceNotifyFlag(Integer voiceNotifyFlag) {
        this.voiceNotifyFlag = voiceNotifyFlag;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
