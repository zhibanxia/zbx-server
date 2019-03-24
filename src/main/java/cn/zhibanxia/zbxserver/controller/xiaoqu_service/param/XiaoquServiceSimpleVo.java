package cn.zhibanxia.zbxserver.controller.xiaoqu_service.param;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zzy on  2019/03/17 15:52
 * 简要的小区服务模型vo：小区服务列表单个信息
 */
public class XiaoquServiceSimpleVo implements Serializable {
    private static final long serialVersionUID = 1156313724512352679L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 服务类别：
     * 1：回收；
     * 2：家政；
     * 3：团购；
     * 4：其他
     */
    private Integer type;
    /**
     * 服务区域，到区县一级，多个以逗号分隔，all表示全部
     */
    private List<String> serviceAreas;
    /**
     * 头像图片
     */
    private String iconImg;
    /**
     * 服务星值
     */
    private String servStarValue;

    /**
     * 联系人名称
     */
    private String contactName;

    /**
     * 联系电话
     */
    private String contactPhone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<String> getServiceAreas() {
        return serviceAreas;
    }

    public void setServiceAreas(List<String> serviceAreas) {
        this.serviceAreas = serviceAreas;
    }

    public String getIconImg() {
        return iconImg;
    }

    public void setIconImg(String iconImg) {
        this.iconImg = iconImg;
    }

    public String getServStarValue() {
        return servStarValue;
    }

    public void setServStarValue(String servStarValue) {
        this.servStarValue = servStarValue;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
