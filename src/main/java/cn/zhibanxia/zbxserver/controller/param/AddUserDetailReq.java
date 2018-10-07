package cn.zhibanxia.zbxserver.controller.param;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 补充详情：业主、回收人员
 * Created by zzy on  2018/10/03 16:28
 */
public class AddUserDetailReq implements Serializable {
    private static final long serialVersionUID = -4304965423851002510L;
    /**
     * 手机号
     */
    @NotEmpty
    private String mobilePhone;

    @NotNull
    private Addr defaultAddr;

    private List<Addr> focusAddrList;

    /**
     * 审核头像id
     */
    private String verifyLogo;

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Addr getDefaultAddr() {
        return defaultAddr;
    }

    public void setDefaultAddr(Addr defaultAddr) {
        this.defaultAddr = defaultAddr;
    }

    public List<Addr> getFocusAddrList() {
        return focusAddrList;
    }

    public void setFocusAddrList(List<Addr> focusAddrList) {
        this.focusAddrList = focusAddrList;
    }

    public String getVerifyLogo() {
        return verifyLogo;
    }

    public void setVerifyLogo(String verifyLogo) {
        this.verifyLogo = verifyLogo;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
