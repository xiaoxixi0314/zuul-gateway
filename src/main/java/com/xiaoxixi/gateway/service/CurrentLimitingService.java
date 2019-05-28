package com.xiaoxixi.gateway.service;

import com.xiaoxixi.gateway.service.model.RequestTokenConfig;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * current limiting service implements
 */
public interface CurrentLimitingService {

    boolean uriHasRequestToken(HttpServletRequest request);

    void configRequestTokenForUri(RequestTokenConfig config);

    void configRequestTokenStrategy(RequestTokenConfig config);

    List<RequestTokenConfig> getAllRequestTokenConfig();

    RequestTokenConfig getRequestTokenConfig(HttpServletRequest request);
}
