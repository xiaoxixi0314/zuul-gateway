package com.github.xiaoxixi.gateway.result;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

    DEGRADE_REQUEST("服务器繁忙，请稍后再试"),
    CURRENT_LIMIT_REQUEST("排队人数过多，请稍后再试"),
    SERVICE_INVALID("服务请求失败，请稍后再试"),

    ILLEGAL_USER("非法用户"),
    ACCESS_TOKEN_EXPIRE("令牌过期"),

    SYSTEM_ERROR("系统错误");

    private String errorCode;
    private String errorMessage;

    ErrorCodeEnum(String errorMessage) {
        this.errorMessage = errorMessage;
        this.errorCode = this.name();
    }
}
