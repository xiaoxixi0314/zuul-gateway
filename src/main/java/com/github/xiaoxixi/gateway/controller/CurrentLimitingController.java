package com.github.xiaoxixi.gateway.controller;

import com.github.xiaoxixi.gateway.result.Result;
import com.github.xiaoxixi.gateway.service.CurrentLimitingService;
import com.github.xiaoxixi.gateway.service.model.RequestTokenConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/limit")
public class CurrentLimitingController {

    @Autowired
    private CurrentLimitingService currentLimitingService;

    @GetMapping("/_list")
    public Result<List<RequestTokenConfig>> list(){
        List<RequestTokenConfig> list = currentLimitingService.getAllRequestTokenConfig();
        Result<List<RequestTokenConfig>> result = Result.success(list);
        return result;
    }
}
