package com.github.xiaoxixi.gateway.service;

import com.alibaba.fastjson.JSON;
import com.github.xiaoxixi.gateway.constant.GatewayConstants;
import com.github.xiaoxixi.gateway.service.model.DegradeRequest;
import com.github.xiaoxixi.gateway.util.UriUtils;
import com.xiaoxixi.service.register.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * degrade service
 */
@Service
public class DegradeServiceImpl implements DegradeService{

    @Autowired
    private RedisService redisService;

    @Override
    public boolean isDegradeRequest(HttpServletRequest request){
        String uri = UriUtils.uriSlashToColon(request.getRequestURI());
        String degradeKey = GatewayConstants.DEGRADE_SERVICE_KEY_PREFIX + uri;
        if (Objects.isNull(redisService.get(degradeKey))) {
            return false;
        }
        return true;
    }

    @Override
    public void addDegradeRequest(String uri, String uriExplain){
        String degradeKey = GatewayConstants.DEGRADE_SERVICE_KEY_PREFIX + UriUtils.uriSlashToColon(uri);
        DegradeRequest request = new DegradeRequest();
        request.setUri(uri);
        request.setUriExplain(uriExplain);
        redisService.setnx(degradeKey, JSON.toJSONString(request));
    }

    @Override
    public void modifyDegradeRequest(String uri, String uriExplain){
        String degradeKey = GatewayConstants.DEGRADE_SERVICE_KEY_PREFIX + UriUtils.uriSlashToColon(uri);
        DegradeRequest request = new DegradeRequest();
        request.setUri(uri);
        request.setUriExplain(uriExplain);
        redisService.set(degradeKey, JSON.toJSONString(request));
    }

    @Override
    public List<DegradeRequest> getAllDegradeRequest(){
        List<DegradeRequest> requests = new ArrayList<>();
        List<String> jsonString = redisService.getByKeyPrefix(GatewayConstants.DEGRADE_SERVICE_KEY_PREFIX);
        for (String json : jsonString) {
            DegradeRequest request = JSON.parseObject(json, DegradeRequest.class);
            requests.add(request);
        }
        return requests;
    }
}
