package com.github.xiaoxixi.gateway.util;

import com.github.xiaoxixi.gateway.constant.GatewayConstants;
import com.github.xiaoxixi.gateway.exception.ParamsException;
import com.github.xiaoxixi.gateway.result.ErrorCodeEnum;
import com.xiaoxixi.service.register.util.StringUtils;

public class LoginUtils {

    public static String buildRedisTokenKey(String accessToken) {
        return new StringBuilder().append(GatewayConstants.REDIS_TOKEN_PREFIX)
                .append(GatewayConstants.REDIS_KEY_SPLIT)
                .append(accessToken)
                .toString();
    }
}
