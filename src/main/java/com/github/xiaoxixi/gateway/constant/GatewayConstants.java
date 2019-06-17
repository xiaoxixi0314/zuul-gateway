package com.github.xiaoxixi.gateway.constant;

public class GatewayConstants {

    public static final String SERVICE_PREFIX  = "/api";

    public static final String SERVICE_NAME_HEADER = "X-request-to";

    public static final String LOGIN_USER_ID_HEADER = "X-request-by";

    public static final String ALL_SERVICE_ID = "all_service";

    public static final String ALL_SERVICE_PATH = "/**";

    public static final String DEGRADE_SERVICE_KEY_PREFIX = "degrade_service_path";

    public static final String CURRENT_LIMITING_KEY_PREFIX = "current_limiting";

    public static final String CURRENT_LIMITING_CONFIG_KEY_PREFIX = "current_limiting_config";

    public static final String REDIS_TOKEN_PREFIX = "ACCESS_TOKEN";

    public static final String REDIS_KEY_SPLIT = ":";

    public static final String TOKEN_COOKIE_NAME = "auth_token___";

}
