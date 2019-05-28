package com.xiaoxixi.gateway.service;

import com.xiaoxixi.gateway.exception.DiscoveryServiceException;
import com.xiaoxixi.service.register.ServiceProperty;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * discovery local service
 */
public interface DiscoveryLocalService {

    /**
     * 发现注册的服务uri
     * @param request
     * @return
     */
    String discoveryLocalService(HttpServletRequest request) throws DiscoveryServiceException;

    /**
     * 发现本地所有可用的服务
     * @return
     */
    List<ServiceProperty> discoveryAllLocalService();

}
