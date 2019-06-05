package com.github.xiaoxixi.gateway;

import com.xiaoxixi.service.register.ServiceRegisterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableZuulProxy
@Import(ServiceRegisterConfig.class)
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
