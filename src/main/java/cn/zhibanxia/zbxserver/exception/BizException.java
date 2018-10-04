package cn.zhibanxia.zbxserver.exception;

import cn.zhibanxia.zbxserver.constant.ErrorCode;

/**
 * Created by zzy on  2018/10/02 12:07
 */
public class BizException extends Exception {
    private final ErrorCode errorCode;

    public BizException(ErrorCode errorCode) {
        super(errorCode.getCode() + ", " + errorCode.getMsg());
        this.errorCode = errorCode;
    }

    public BizException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getCode() + ", " + errorCode.getMsg(), cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
