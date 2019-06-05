package com.github.xiaoxixi.gateway.service;

import com.github.xiaoxixi.gateway.service.model.RequestTokenConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 限流接口
 */
public interface CurrentLimitingService {

    /**
     * 当前请求是否有请求令牌
     * @param request
     * @return
     */
    boolean uriHasRequestToken(HttpServletRequest request);

    /**
     * 设置uri的限流配置
     * @param config
     */
    void configRequestTokenForUri(RequestTokenConfig config);

    /**
     * 设置限流策略，单位时间内可请求数量
     * @param config
     */
    void configRequestTokenStrategy(RequestTokenConfig config);

    /**
     * 获取所有uri的限流配置
     * @return
     */
    List<RequestTokenConfig> getAllRequestTokenConfig();

    RequestTokenConfig getRequestTokenConfig(HttpServletRequest request);
}
