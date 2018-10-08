package cn.zhibanxia.zbxserver.controller.param;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zzy on  2018/10/08 22:44
 */
public class ListRsp<T> implements Serializable {
    private static final long serialVersionUID = -6523208816247627084L;
    private int totalCount;
    private List<T> list;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
