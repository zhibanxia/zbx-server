package cn.zhibanxia.zbxserver.controller.param;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * 回收员信息
 * Created by zzy on  2019/04/06 14:57
 */
public class HsuRecommBo implements Serializable {

    private static final long serialVersionUID = -3349586977164663174L;
    /**
     * 回收员联系人
     */
    private String contactName;
    /**
     * 回收员联系电话
     */
    private String contactPhone;
    /**
     * 回收员备注
     */
    private String serviceDesc;

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

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
