package com.github.xiaoxixi.gateway.util;


import com.xiaoxixi.service.register.util.StringUtils;

public class UriUtils {

    public static String uriSlashToColon(String uri) {
        if (StringUtils.isEmpty(uri)) {
            return uri;
        }
        return uri.replaceAll("/", ":");
    }
}
