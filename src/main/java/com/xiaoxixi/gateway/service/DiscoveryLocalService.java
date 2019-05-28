package com.xiaoxixi.gateway.service;

import com.xiaoxixi.gateway.exception.DiscoveryServiceException;

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
    String discoveryLocalService(HttpServletRequest request) throws DiscoveryServiceException;

}
