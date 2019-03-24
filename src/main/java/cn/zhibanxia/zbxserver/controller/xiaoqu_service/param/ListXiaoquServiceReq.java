package cn.zhibanxia.zbxserver.controller.xiaoqu_service.param;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Created by zzy on  2019/03/17 16:38
 */
public class ListXiaoquServiceReq implements Serializable {
    private static final long serialVersionUID = -2206215646104060168L;
    /**
     * 服务类别：
     * 1：回收；
     * 2：家政；
     * 3：团购；
     * 4：其他
     */
    private Integer type;

    /**
     * 分页页码
     */
    private Integer page;

    /**
     * 每页展示条数
     */
    private Integer size;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
