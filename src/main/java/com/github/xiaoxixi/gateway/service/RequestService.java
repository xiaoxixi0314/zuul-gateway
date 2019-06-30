package com.github.xiaoxixi.gateway.service;

import com.netflix.zuul.context.RequestContext;

@FunctionalInterface
public interface RequestService {

    /**
     * check request
     * @param context
     */
    boolean validate(RequestContext context);
}
