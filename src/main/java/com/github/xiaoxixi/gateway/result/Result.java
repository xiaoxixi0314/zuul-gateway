package com.github.xiaoxixi.gateway.result;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Result<T> {

    @Setter
    private T data;

    private StatusInfo result;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.result = StatusInfo.success();
        result.data = data;

        return result;
    }

    public static <T> Result<T> error(String errCode, String errMsg) {
        Result<T> result = new Result<>();
        result.result = StatusInfo.error(errCode, errMsg);
        return result;
    }

    public static <T> Result<T> error(String errMsg) {
        Result<T> result = new Result<>();
        result.result = StatusInfo.error("BIZ_ERROR", errMsg);
        return result;
    }

    public static <T> Result<T> error(T data,String errCode,String errMsg){
        Result<T> result = new Result<>();
        result.result = StatusInfo.error(errCode, errMsg);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(StatusInfo errorInfo) {
        Result<T> result = new Result<>();
        result.result = StatusInfo.error(errorInfo.errorCode, errorInfo.errorMsg);
        return result;
    }

    public static <T> Result<T> error(ErrorCodeEnum errorCodeEnum) {
        Result<T> result = new Result<>();
        result.result = StatusInfo.error(errorCodeEnum.getErrorCode(), errorCodeEnum.getErrorMessage());
        return result;
    }

    @Setter
    @Getter
    public static class StatusInfo {

        private boolean success;

        private String errorCode;

        private String errorMsg;

        private static StatusInfo success() {
            StatusInfo info = new StatusInfo();
            info.success = true;
            return info;
        }

        public static StatusInfo error(String errCode, String errMsg) {
            StatusInfo info = new StatusInfo();
            info.success = false;
            info.errorCode = errCode;
            info.errorMsg = errMsg;
            return info;
        }
    }
}
