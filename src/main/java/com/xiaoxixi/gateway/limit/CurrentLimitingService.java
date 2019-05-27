package com.xiaoxixi.gateway.limit;

import com.xiaoxixi.gateway.model.RequestTokenConfig;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * current limiting service implements
 */
public interface CurrentLimitingService {

    boolean hasRequestToken(HttpServletRequest request);

    void configRequestTokenForUri(RequestTokenConfig config);

    List<RequestTokenConfig> getAllRequestTokenConfig();

    RequestTokenConfig getRequestTokenConfig(HttpServletRequest request);
}
