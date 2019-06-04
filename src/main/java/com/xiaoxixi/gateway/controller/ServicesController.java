package com.xiaoxixi.gateway.controller;

import com.xiaoxixi.gateway.exception.DiscoveryServiceException;
import com.xiaoxixi.gateway.result.Result;
import com.xiaoxixi.gateway.service.DiscoveryLocalService;
import com.xiaoxixi.gateway.service.model.ServiceWeightChangeRequest;
import com.xiaoxixi.service.register.ServiceProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/service")
public class ServicesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServicesController.class);

    @Autowired
    private DiscoveryLocalService discoveryLocalService;

    /**
     * 获取所有已注册的服务
     * @return
     */
    @GetMapping("/_list")
    public Result<List<ServiceProperty>> findAllServices(){
        List<ServiceProperty> list = discoveryLocalService.discoveryAllLocalService();
        Result<List<ServiceProperty>> result = Result.success(list);
        return result;
    }

    /**
     * 设置服务权重
     * @param weight
     * @return
     */
    @PostMapping("/_weight")
    public Result<Boolean> weight(ServiceWeightChangeRequest weight) {
        try {
            Boolean result = discoveryLocalService.changeServiceWeight(weight);
            return Result.success(result);
        } catch (DiscoveryServiceException dse) {
            LOGGER.error("change service weight error:", dse);
            return Result.error(dse.getMessage());
        }
    }

}
