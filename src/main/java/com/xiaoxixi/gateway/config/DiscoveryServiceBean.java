package com.xiaoxixi.gateway.config;

import com.xiaoxixi.service.register.DiscoveryService;
import com.xiaoxixi.service.register.ServiceRegisterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiscoveryServiceBean {

    @Autowired
    private ServiceRegisterConfig serviceRegisterConfig;

    @Bean
    public DiscoveryService discoveryService(){
        return serviceRegisterConfig.getDiscoveryService();
    }
}
