package cn.zhibanxia.zbxserver.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zzy on  2018/11/18 15:14
 */
public class ComplexEntity implements Serializable {
    private static final long serialVersionUID = -2089441273748506656L;
    /**
     * 主键id
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
    private Date gmtCreate;
    private Date gmtModified;

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

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
