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
     * 用户类型：1.业主；2.回收人员；3.管理员
     */
    private Integer type;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
