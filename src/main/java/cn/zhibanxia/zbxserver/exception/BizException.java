package cn.zhibanxia.zbxserver.exception;

/**
 * Created by zzy on  2018/10/02 12:07
 */
public class BizException extends Exception {
    private final String code;
    private final String msg;

    public BizException(String code, String msg) {
        super("code=" + code + ", msg={}" + msg);
        this.code = code;
        this.msg = msg;
    }

    public BizException(String code, String msg, Throwable cause) {
        super(code + ", " + msg, cause);
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
