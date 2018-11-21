package cn.zhibanxia.zbxserver.controller.param;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.validation.constraints.NotEmpty;
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
     * 省
     */
    @NotEmpty
    private String provinceId;
    /**
     * 市
     */
    @NotEmpty
    private String cityId;
    /**
     * 区
     */
    @NotEmpty
    private String areaId;
    /**
     * 街道
     */
    @NotEmpty
    private String subdistrictId;
    /**
     * 详细地址
     */
    @NotEmpty
    private String addrDetail;

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

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getSubdistrictId() {
        return subdistrictId;
    }

    public void setSubdistrictId(String subdistrictId) {
        this.subdistrictId = subdistrictId;
    }

    public String getAddrDetail() {
        return addrDetail;
    }

    public void setAddrDetail(String addrDetail) {
        this.addrDetail = addrDetail;
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
