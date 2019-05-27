package com.xiaoxixi.gateway.degrade;

import com.xiaoxixi.gateway.constant.GatewayConstants;
import com.xiaoxixi.service.register.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
        String uri = request.getRequestURI().replaceAll("/", ":");
        String degradeKey = GatewayConstants.DEGRADE_SERVICE_KEY_PREFIX + uri;
        if (Objects.isNull(redisService.get(degradeKey))) {
            return false;
        }
        return true;
    }
}
