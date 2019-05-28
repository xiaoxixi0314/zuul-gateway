package com.xiaoxixi.gateway.service;

import com.alibaba.fastjson.JSON;
import com.xiaoxixi.gateway.constant.GatewayConstants;
import com.xiaoxixi.gateway.exception.ParamsException;
import com.xiaoxixi.gateway.service.model.RequestTokenConfig;
import com.xiaoxixi.gateway.util.UriUtils;
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
    public boolean uriHasRequestToken(HttpServletRequest request){
        String uri = UriUtils.uriSlashToColon(request.getRequestURI());
        String tokenKey = GatewayConstants.CURRENT_LIMITING_KEY_PREFIX + uri;
        RequestTokenConfig tokenConfig = getRequestTokenConfig(request);
        // 没有配置限流策略，则不限流
        if (Objects.isNull(tokenConfig)) {
            return true;
        }
        if (!redisService.exists(tokenKey)) {
            configRequestTokenStrategy(tokenConfig);
        }
        Long tokenCount = redisService.decr(tokenKey);
        if (tokenCount.longValue() < 0L) {
            LOGGER.warn("request has no request token:{}", uri);
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
                + UriUtils.uriSlashToColon(config.getUri());
        redisService.set(tokenConfigKey, JSON.toJSONString(config));
    }

    @Override
    public void configRequestTokenStrategy(RequestTokenConfig config){
        String tokenStrategyKey = GatewayConstants.CURRENT_LIMITING_KEY_PREFIX + UriUtils.uriSlashToColon(config.getUri());
        redisService.setnx(tokenStrategyKey, config.getTokenCount().toString(), config.getUnitTime());
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
                GatewayConstants.CURRENT_LIMITING_CONFIG_KEY_PREFIX + UriUtils.uriSlashToColon(uri);
        String configJson = redisService.get(tokenConfigKey);
        if (StringUtils.isEmpty(configJson)) {
            return null;
        }
        return JSON.parseObject(configJson, RequestTokenConfig.class);
    }

}
