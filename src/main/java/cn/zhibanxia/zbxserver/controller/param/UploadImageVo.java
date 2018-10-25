package cn.zhibanxia.zbxserver.controller.param;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Created by zzy on  2018/10/25 20:07
 */
public class UploadImageVo implements Serializable {
    private static final long serialVersionUID = 8868480504588473550L;
    /**
     * 图片base64
     */
    private String imgBase64;
    /**
     * 业务类型
     */
    private Integer bizType;

    public String getImgBase64() {
        return imgBase64;
    }

    public void setImgBase64(String imgBase64) {
        this.imgBase64 = imgBase64;
    }

    public Integer getBizType() {
        return bizType;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
