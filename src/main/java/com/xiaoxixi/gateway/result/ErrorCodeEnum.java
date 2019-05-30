package com.xiaoxixi.gateway.result;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

    DEGRADE_REQUEST("DEGRADE_REQUEST", "服务器繁忙，请稍后再试"),
    CURRENT_LIMIT_REQUEST("CURRENT_LIMIT_REQUEST", "排队人数过多，请稍后再试"),
    SERVICE_INVALID("SERVICE_INVALID", "服务请求失败，请稍后再试");

    private String errorCode;
    private String errorMessage;

    ErrorCodeEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
