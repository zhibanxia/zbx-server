package cn.zhibanxia.zbxserver.bo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Created by zzy on  2018/12/31 20:55
 */
public class SearchUserBo implements Serializable {
    private static final long serialVersionUID = -2086954380968653384L;
    /**
     * sql limit start
     */
    private Integer startPage;

    /**
     * sql limit size
     */
    private Integer size;
    /**
     * 用户类型：1.业主；2.回收人员；3.管理员
     */
    private Integer userType;

    /**
     * 用户状态：
     * 1.正常
     * 2.待提交审核
     * 3.审核不通过，请重新提交审核
     * 4.审核中
     * 5.禁用
     * 6.注销
     */
    private Integer userStatus;

    /**
     * 搜索类型：
     * 1.昵称
     * 2.手机号码
     */
    private Integer searchType;

    /**
     * 搜索内容，与搜索类型确定唯一的搜索主题和内容
     */
    private String searchContent;

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
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
