package com.xiaoxixi.gateway.controller;

import com.xiaoxixi.service.register.DiscoveryService;
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
    private DiscoveryService discoveryService;

    @GetMapping("/_all")
    public List<ServiceProperty> findAllServices(){
        return discoveryService.discoveryAllServices();
    }
}
