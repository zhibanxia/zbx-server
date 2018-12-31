package cn.zhibanxia.zbxserver.controller.param;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Created by zzy on  2018/12/31 18:19
 */
public class SearchUserReq implements Serializable {
    private static final long serialVersionUID = -5174161982132759914L;

    /**
     * 分页查询参数，页码
     */
    private Integer page = 1;

    /**
     * 分页查询参数，每页数据条数
     */
    private Integer size = 10;
    /**
     * 用户类型：1.业主；2.回收人员；3.管理员
     */
    private Integer userType = 0;

    /**
     * 用户状态：
     * 1.正常
     * 2.待提交审核
     * 3.审核不通过，请重新提交审核
     * 4.审核中
     * 5.禁用
     * 6.注销
     */
    private Integer userStatus = 0;

    /**
     * 搜索类型：
     * 1.昵称
     * 2.手机号码
     */
    private Integer searchType = 1;

    /**
     * 搜索内容，与搜索类型确定唯一的搜索主题和内容
     */
    private String searchContent;

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

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getSearchType() {
        return searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
