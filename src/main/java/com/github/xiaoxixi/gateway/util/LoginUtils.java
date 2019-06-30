package com.github.xiaoxixi.gateway.util;

import com.github.xiaoxixi.gateway.constant.GatewayConstants;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class LoginUtils {

    private static final List<String> IGNORE_TOKEN_URLS =
            Arrays.asList("/api/auth/login"
            ,"/api/auth/refresh/token");

    public static boolean isIgnoreCheckAccessToken(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (IGNORE_TOKEN_URLS.contains(uri)) {
            return true;
        }
        return false;
    }

    public static String buildRedisTokenKey(String accessToken) {
        return new StringBuilder().append(GatewayConstants.REDIS_TOKEN_PREFIX)
                .append(GatewayConstants.REDIS_KEY_SPLIT)
                .append(accessToken)
                .toString();
    }
}
