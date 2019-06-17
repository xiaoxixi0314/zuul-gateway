package com.github.xiaoxixi.gateway.service;

import com.alibaba.fastjson.JSON;
import com.github.xiaoxixi.gateway.constant.GatewayConstants;
import com.github.xiaoxixi.gateway.result.ErrorCodeEnum;
import com.github.xiaoxixi.gateway.result.Result;
import com.github.xiaoxixi.gateway.service.model.UserAccessToken;
import com.github.xiaoxixi.gateway.util.LoginUtils;
import com.xiaoxixi.service.register.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private RedisService redisService;

    @Override
    public Result<Boolean> checkAccessToken(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, GatewayConstants.TOKEN_COOKIE_NAME);
        if (Objects.isNull(cookie)) {
            return Result.error(ErrorCodeEnum.ILLEGAL_USER);
        }
        String accessToken = cookie.getValue();
        String redisTokenKey = LoginUtils.buildRedisTokenKey(accessToken);
        String tokenObjectJson = redisService.get(redisTokenKey);
        if (StringUtils.isEmpty(tokenObjectJson)) {
            return Result.error(ErrorCodeEnum.ILLEGAL_USER);
        }
        UserAccessToken token = JSON.parseObject(tokenObjectJson, UserAccessToken.class);
        if (Objects.isNull(token)) {
            return Result.error(ErrorCodeEnum.ILLEGAL_USER);
        }
        if (tokenIsExpire(token)) {
            return Result.error(ErrorCodeEnum.ACCESS_TOKEN_EXPIRE);
        }
        return Result.success(Boolean.TRUE);
    }

    private boolean tokenIsExpire(UserAccessToken token) {
        Date now = new Date();
        Date expireAt = token.getExpireAt();
        if (now.compareTo(expireAt) >= 0) {
            return true;
        }
        return false;
    }
}
