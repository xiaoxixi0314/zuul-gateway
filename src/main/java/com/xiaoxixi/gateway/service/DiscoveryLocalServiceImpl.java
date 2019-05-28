package com.xiaoxixi.gateway.service;

import com.alibaba.fastjson.JSON;
import com.xiaoxixi.gateway.constant.GatewayConstants;
import com.xiaoxixi.gateway.exception.DiscoveryServiceException;
import com.xiaoxixi.gateway.exception.ParamsException;
import com.xiaoxixi.service.register.DiscoveryService;
import com.xiaoxixi.service.register.ServiceProperty;
import com.xiaoxixi.service.register.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * discovery local service implements
 */
@Service
public class DiscoveryLocalServiceImpl implements DiscoveryLocalService{

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscoveryLocalServiceImpl.class);

    @Autowired
    private DiscoveryService discoveryService;

    @Value("${register.service.prefix}")
    private String servicePrefix;

    @Override
    public String discoveryLocalService(HttpServletRequest request) throws DiscoveryServiceException{
        String serviceName = request.getHeader(GatewayConstants.SERVICE_NAME_HEADER);
        if (StringUtils.isEmpty(serviceName)) {
            throw new ParamsException("service name can't be null or empty, please check request has [X-request-to] header.");
        }
        ServiceProperty service = discoveryService.discoveryService(servicePrefix, serviceName);
        if (Objects.isNull(service)) {
            throw new DiscoveryServiceException("can't discovery service, service name:" +  serviceName);
        }
        LOGGER.info("<<<<found service:{}", JSON.toJSONString(service));
        String uri = request.getRequestURI();
        return buildLocalServiceUrl(service, uri);
    }

    /**
     * build local service url
     * @param service
     * @param gatewayUri
     * @return
     */
    private String buildLocalServiceUrl(ServiceProperty service, String gatewayUri) {
        String localServiceBindUrl = service.getServiceBindUrl();
        String remoteUriSuffix =
                gatewayUri.substring(GatewayConstants.SERVICE_PREFIX.length(), gatewayUri.length());
        return localServiceBindUrl + remoteUriSuffix;
    }

}
