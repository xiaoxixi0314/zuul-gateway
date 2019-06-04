package com.xiaoxixi.gateway.controller;

import com.xiaoxixi.gateway.result.Result;
import com.xiaoxixi.gateway.service.DegradeService;
import com.xiaoxixi.gateway.service.model.DegradeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/degrade")
public class DegradeController {

    @Autowired
    private DegradeService degradeService;

    /**
     * 获取已降级的路径列表
     * @return
     */
    @GetMapping("/_list")
    public Result<List<DegradeRequest>> list(){
        List<DegradeRequest> list = degradeService.getAllDegradeRequest();
        Result<List<DegradeRequest>> result = Result.success(list);
        return result;
    }
}
