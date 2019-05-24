package com.xiaoxixi.gateway.config;

import com.xiaoxixi.service.register.DiscoveryService;
import com.xiaoxixi.service.register.ServiceRegisterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Configuration
public class ZuulBeanDefine {

    private static final Route route=  new Route("all_service",
            "/**",
            "",
            "/api",
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
}
