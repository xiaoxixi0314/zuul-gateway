package com.xiaoxixi.gateway.degrade;

import javax.servlet.http.HttpServletRequest;

/**
 * degrade service
 */
public interface DegradeService {

    boolean isDegradeRequest(HttpServletRequest request);
}
