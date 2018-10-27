package cn.zhibanxia.zbxserver.controller.param;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Created by zzy on  2018/10/27 22:45
 */
public class VerifyHuishouVo implements Serializable {
    private static final long serialVersionUID = 1314885804417461087L;
    /**
     * 回收人员id
     */
    private Long id;
    /**
     * 审核结果
     */
    private Boolean verifyResult;

    /**
     * 审核拒绝原因
     */
    private String verifyRemark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getVerifyResult() {
        return verifyResult;
    }

    public void setVerifyResult(Boolean verifyResult) {
        this.verifyResult = verifyResult;
    }

    public String getVerifyRemark() {
        return verifyRemark;
    }

    public void setVerifyRemark(String verifyRemark) {
        this.verifyRemark = verifyRemark;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
