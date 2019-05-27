package com.xiaoxixi.gateway.discovery;

import javax.servlet.http.HttpServletRequest;

/**
 * discovery local service
 */
public interface DiscoveryLocalService {

    /**
     * discovery local service uri
     * @param request
     * @return
     */
    String discoveryLocalService(HttpServletRequest request);

}
