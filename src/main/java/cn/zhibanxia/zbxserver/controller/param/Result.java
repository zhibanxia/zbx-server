package cn.zhibanxia.zbxserver.controller.param;

import cn.zhibanxia.zbxserver.constant.ErrorCode;
import cn.zhibanxia.zbxserver.exception.BizException;

/**
 * Created by zzy on  2018/10/03 11:21
 */
public class Result<T> {


    private boolean success;
    private String code;
    private String desc;
    private T data;

    protected Result() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 结果构造器
     */
    public static class ResultBuilder {
        public static <T> Result<T> success(T data) {
            Result<T> result = new Result<>();
            result.setSuccess(true);
            result.setCode(ErrorCode.CODE_SUCCESS.getCode());
            result.setData(data);
            return result;
        }

        public static <T> Result<T> fail(ErrorCode errorCode) {
            Result<T> result = new Result<>();
            result.setSuccess(false);
            result.setCode(errorCode.getCode());
            result.setDesc(errorCode.getMsg());
            return result;
        }

        public static <T> Result<T> fail(BizException e) {
            Result<T> result = new Result<>();
            result.setSuccess(false);
            result.setCode(e.getErrorCode().getCode());
            result.setDesc(e.getErrorCode().getMsg());
            return result;
        }
    }
}
