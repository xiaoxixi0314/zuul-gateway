package com.xiaoxixi.gateway.limit;

import com.alibaba.fastjson.JSON;
import com.xiaoxixi.gateway.constant.GatewayConstants;
import com.xiaoxixi.gateway.exception.ParamsException;
import com.xiaoxixi.gateway.model.RequestTokenConfig;
import com.xiaoxixi.service.register.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * current limiting service implement
 */
@Service
public class CurrentLimitingServiceImpl implements CurrentLimitingService{

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentLimitingServiceImpl.class);

    @Autowired
    private RedisService redisService;

    @Override
    public boolean hasRequestToken(HttpServletRequest request){
        String uri = request.getRequestURI().replaceAll("/", ":");
        String tokenKey = GatewayConstants.CURRENT_LIMITING_KEY_PREFIX + uri;

        Long tokenCount = redisService.decr(tokenKey);
        if (tokenCount.longValue() < 0L) {
            return false;
        }
        return true;
    }

    @Override
    public void configRequestTokenForUri(RequestTokenConfig config){
        if (Objects.isNull(config)
                || StringUtils.isEmpty(config.getUri())) {
            throw new ParamsException("request token config can't be null");
        }
        String tokenConfigKey = GatewayConstants.CURRENT_LIMITING_CONFIG_KEY_PREFIX
                + config.getUri().replaceAll("/" , ":");
        redisService.set(tokenConfigKey, JSON.toJSONString(config));
    }

    @Override
    public List<RequestTokenConfig> getAllRequestTokenConfig(){
        List<String> configsJson = redisService.getByKeyPrefix(GatewayConstants.CURRENT_LIMITING_KEY_PREFIX);
        if (CollectionUtils.isEmpty(configsJson)) {
            return Collections.emptyList();
        }
        List<RequestTokenConfig> configs  = new ArrayList<>();
        for (String configJson : configsJson) {
            if (StringUtils.isEmpty(configJson)) {
                continue;
            }
            RequestTokenConfig config = JSON.parseObject(configJson, RequestTokenConfig.class);
            configs.add(config);
        }
        return configs;
    }

    @Override
    public RequestTokenConfig getRequestTokenConfig(HttpServletRequest request){
        String uri = request.getRequestURI();
        String tokenConfigKey =
                GatewayConstants.CURRENT_LIMITING_CONFIG_KEY_PREFIX + uri.replaceAll("/", ":");
        String configJson = redisService.get(tokenConfigKey);
        if (StringUtils.isEmpty(configJson)) {
            return null;
        }
        return JSON.parseObject(configJson, RequestTokenConfig.class);
    }

}
