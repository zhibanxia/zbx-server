package cn.zhibanxia.zbxserver.controller.param;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Created by zzy on  2019/04/06 14:49
 */
public class HsuRecommReqVo implements Serializable {
    private static final long serialVersionUID = -5211661611542404049L;
    /**
     * 回收订单id
     */
    private Long recId;

    public Long getRecId() {
        return recId;
    }

    public void setRecId(Long recId) {
        this.recId = recId;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
