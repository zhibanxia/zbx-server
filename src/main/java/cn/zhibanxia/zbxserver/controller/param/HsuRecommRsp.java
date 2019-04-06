package cn.zhibanxia.zbxserver.controller.param;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zzy on  2019/04/06 14:56
 */
public class HsuRecommRsp implements Serializable {
    private static final long serialVersionUID = 2837173697950737010L;
    /**
     * 小区名称
     */
    private String complexName;
    /**
     * 推荐的回收人员列表
     */
    private List<HsuRecommBo> hsuRecommList;

    public String getComplexName() {
        return complexName;
    }

    public void setComplexName(String complexName) {
        this.complexName = complexName;
    }

    public List<HsuRecommBo> getHsuRecommList() {
        return hsuRecommList;
    }

    public void setHsuRecommList(List<HsuRecommBo> hsuRecommList) {
        this.hsuRecommList = hsuRecommList;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
