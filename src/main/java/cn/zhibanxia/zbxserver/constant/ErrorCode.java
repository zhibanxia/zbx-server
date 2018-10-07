package cn.zhibanxia.zbxserver.constant;

/**
 * Created by zzy on  2018/10/02 12:11
 * <p>
 * 1xxxxx   http请求错误
 * 1x2xxx   json解析错误
 * 1x3xxx   oss上传文件错误
 * 1x4xxx   上传文件格式不合法
 * 2xxxxx   用户相关错误
 * 3xxxxx   参数不合法
 * 4xxxxx   回收业务相关
 * 9xxxxx   系统相关
 */
public enum ErrorCode {

    /**
     * 成功
     */
    CODE_SUCCESS("000000", "ok"),

    /**
     * http GET 请求出错
     */
    CODE_HTTP_GET_ERROR("101001", "GET请求出错"),

    /**
     * http POST 请求出错
     */
    CODE_HTTP_POST_ERROR("101002", "POST请求出错"),

    /**
     * json字符串解析出错
     */
    CODE_JSON_PASER_ERROR("102001", "JSON解析出错"),

    /**
     * oss上传文件出错
     */
    CODE_OSS_UPLOAD_ERROR("103001", "oss上传文件出错"),

    /**
     * 上传文件格式不合法
     */
    CODE_INVALID_IMG_TYPE_ERROR("104001", "文件格式不合法"),

    /**
     * json字符串解析出错
     */
    CODE_USER_CANNOT_MODIFY_ADDR_ERROR("201001", "资料已审核通过，不能修改"),

    /**
     * 参数不合法
     */
    CODE_INVALID_PARAM_ERROR("301001", "参数不合法"),

    /**
     * 回收请求已经被处理
     */
    CODE_RECYCLE_HAS_HANDLED_ERROR("401001", "回收已经被处理"),


    /**
     * 回收请求已经被处理
     */
    CODE_TOO_MANY_FOCUS_ADDR_ERROR("402001", "关注的回收地址过多"),

    /**
     * 未知错误
     */
    CODE_UNKONWN_ERROR("999999", "系统错误"),
    /**
     * 不支持的操作
     */
    CODE_UNSUPPORTED_OPERATION_ERROR("901001", "不支持的操作");


    private String code;
    private String msg;

    ErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
