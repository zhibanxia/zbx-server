package cn.zhibanxia.zbxserver.controller.xiaoqu_service.param;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Created by zzy on  2019/03/17 16:51
 */
public class DetailXiaoquServiceReq implements Serializable {
    private static final long serialVersionUID = 9154353804473642008L;
    /**
     * 主键id
     */
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
