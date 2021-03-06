package com.github.xiaoxixi.gateway.config;

import com.github.xiaoxixi.gateway.constant.GatewayConstants;
import com.xiaoxixi.service.register.DiscoveryService;
import com.xiaoxixi.service.register.ServiceRegisterConfig;
import com.xiaoxixi.service.register.redis.RedisService;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class ZuulBeanDefine {

    private static final Set<String> ignoredHeaders = new HashSet<>();

    private static final Route route =  new Route(GatewayConstants.ALL_SERVICE_ID,
            GatewayConstants.ALL_SERVICE_PATH,
            "http://localhost:9005",
            GatewayConstants.SERVICE_PREFIX,
            true,
            ignoredHeaders);

    @Autowired
    private ServiceRegisterConfig serviceRegisterConfig;

    @Bean
    public DiscoveryService discoveryService(){
        return serviceRegisterConfig.getDiscoveryService();
    }

    @Bean
    public RedisService redisService() {
        return serviceRegisterConfig.getRedisService();
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
