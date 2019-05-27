package com.xiaoxixi.gateway.config;

import com.xiaoxixi.gateway.constant.GatewayConstants;
import com.xiaoxixi.service.register.DiscoveryService;
import com.xiaoxixi.service.register.ServiceRegisterConfig;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Configuration
public class ZuulBeanDefine {

    private static final Route route=  new Route(GatewayConstants.ALL_SERVICE_ID,
            GatewayConstants.ALL_SERVICE_PATH,
            "http://localhost:9005",
            GatewayConstants.SERVICE_PREFIX,
            true,
            null
    );

    @Autowired
    private ServiceRegisterConfig serviceRegisterConfig;

    @Bean
    public DiscoveryService discoveryService(){
        return serviceRegisterConfig.getDiscoveryService();
    }

    @Bean
    public RouteLocator routeLocator() {
        return new RouteLocator() {
            @Override
            public Collection<String> getIgnoredPaths() {
                return Arrays.asList("/static/**");
            }

            @Override
            public List<Route> getRoutes() {
                return Arrays.asList(route);
            }

            @Override
            public Route getMatchingRoute(String path) {
                return route;
            }
        };
    }

    @Bean
    public OkHttpClient okHttpClient(){
        return new OkHttpClient.Builder().build();
    }
}
