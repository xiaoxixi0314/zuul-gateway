package com.xiaoxixi.gateway.service;

import com.alibaba.fastjson.JSON;
import com.xiaoxixi.gateway.constant.GatewayConstants;
import com.xiaoxixi.gateway.exception.DiscoveryServiceException;
import com.xiaoxixi.gateway.exception.ParamsException;
import com.xiaoxixi.gateway.service.model.ServiceWeightChangeRequest;
import com.xiaoxixi.service.register.DiscoveryService;
import com.xiaoxixi.service.register.ServiceProperty;
import com.xiaoxixi.service.register.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
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
            throw new ParamsException("service name can't be null");
        }
        ServiceProperty service = discoveryService.discoveryService(servicePrefix, serviceName);
        if (Objects.isNull(service)) {
            throw new DiscoveryServiceException(String.format("can't find %s service", serviceName));
        }
        LOGGER.info("<<<<found service:{}", JSON.toJSONString(service));
        String uri = request.getRequestURI();
        return buildLocalServiceUrl(service, uri);
    }

    @Override
    public List<ServiceProperty> discoveryAllLocalService(){
        return discoveryService.discoveryAllServices();
    }

    @Override
    public boolean changeServiceWeight(ServiceWeightChangeRequest request) throws DiscoveryServiceException{
        if (Objects.isNull(request)
                || StringUtils.isEmpty(request.getServiceKey())
                || Objects.isNull(request.getWeight())) {
            throw new ParamsException("change service weight params null");
        }
        return discoveryService.changeServcieWeight(request.getServiceKey(), request.getWeight());
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
