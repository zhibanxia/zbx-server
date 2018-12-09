package cn.zhibanxia.zbxserver.controller.param;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Created by zzy on  2018/11/18 18:51
 */
public class ComplexVo implements Serializable {
    private static final long serialVersionUID = 1174299488652691456L;

    /**
     * 小区id
     */
    private Long id;
    /**
     * 省份id
     */
    private String provinceId;
    /**
     * 地级市id
     */
    private String cityId;
    /**
     * 区id
     */
    private String areaId;
    /**
     * 街道id
     */
    private String subdistrictId;

    /**
     * 板块，比街道更小一级
     */
    private String addrBlock;
    /**
     * 详细地址
     */
    private String addrDetail;
    /**
     * 小区名称
     */
    private String complexName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAddrBlock() {
        return addrBlock;
    }

    public void setAddrBlock(String addrBlock) {
        this.addrBlock = addrBlock;
    }

    public String getAddrDetail() {
        return addrDetail;
    }

    public void setAddrDetail(String addrDetail) {
        this.addrDetail = addrDetail;
    }

    public String getComplexName() {
        return complexName;
    }

    public void setComplexName(String complexName) {
        this.complexName = complexName;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
