package cn.zhibanxia.zbxserver.controller.param;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * 用户cookie属性
 * Created by zzy on  2018/10/02 14:07
 */
public class UserCookieVo implements Serializable {
    private static final long serialVersionUID = -2147979479975976184L;
    /**
     * user id
     */
    private Long uid;
    /**
     * 时间戳
     */
    private Long ts;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
