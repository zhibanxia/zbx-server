package cn.zhibanxia.zbxserver.constant;

/**
 * Created by zzy on  2018/10/03 15:58
 */
public interface UrlConstant {
    /**
     * 没有cookie访问需要鉴权的页面，重定向到错误页
     */
    String NOT_ALLOW = "/pages/not_allow";

    /**
     * 业主发布回收请求
     */
    String YEZHU_ADD_RECYLE_REQUEST = "/recyle/add";

    /**
     * 回收请求列表：业主：发布的历史；回收人员：待回收的请求、已经确认的回收列表
     */
    String RECYLE_REQUEST_LIST = "/recyle/list";

    /**
     * 用户详情页：业主详情页、回收人员详情页
     */
    String USER_DETAIL = "/user/detail";

    /**
     * 审核被拒绝
     */
    String PERFIM_REFUSE = "/user/perfim_refuse";
}
