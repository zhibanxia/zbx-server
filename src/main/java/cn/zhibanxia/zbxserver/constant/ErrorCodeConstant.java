package cn.zhibanxia.zbxserver.constant;

/**
 * Created by zzy on  2018/10/02 12:11
 */
public interface ErrorCodeConstant {
    /**
     * http GET 请求出错
     */
    String CODE_HTTP_GET_ERROR = "101001";
    /**
     * http POST 请求出错
     */
    String CODE_HTTP_POST_ERROR = "101002";

    /**
     * json字符串解析出错
     */
    String CODE_JSON_PASER_ERROR = "102001";

    /**
     * 未知错误
     */
    String CODE_UNKONWN_ERROR = "999999";
}
