package com.xiaoxixi.gateway.controller;

import com.xiaoxixi.gateway.service.DiscoveryLocalService;
import com.xiaoxixi.service.register.ServiceProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/service")
public class ServicesController {

    @Autowired
    private DiscoveryLocalService discoveryLocalService;

    /**
     * 获取所有已注册的服务
     * @return
     */
    @GetMapping("/_all")
    public List<ServiceProperty> findAllServices(){
        return discoveryLocalService.discoveryAllLocalService();
    }
}
