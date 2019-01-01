package cn.zhibanxia.zbxserver.controller.param;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Created by zzy on  2018/10/03 17:03
 */
public class Addr implements Serializable {
    private static final long serialVersionUID = 5897658003434908533L;

    /**
     * 地址库id
     */
    private Long addrId;

    /**
     * 小区id
     */
    private Long complexId;

    /**
     * 小区信息
     */
    private ComplexVo complexVo;
    /**
     * 门牌号
     */
    private String doorInfo;

    public Long getAddrId() {
        return addrId;
    }

    public void setAddrId(Long addrId) {
        this.addrId = addrId;
    }

    public Long getComplexId() {
        return complexId;
    }

    public void setComplexId(Long complexId) {
        this.complexId = complexId;
    }

    public ComplexVo getComplexVo() {
        return complexVo;
    }

    public void setComplexVo(ComplexVo complexVo) {
        this.complexVo = complexVo;
    }

    public String getDoorInfo() {
        return doorInfo;
    }

    public void setDoorInfo(String doorInfo) {
        this.doorInfo = doorInfo;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
