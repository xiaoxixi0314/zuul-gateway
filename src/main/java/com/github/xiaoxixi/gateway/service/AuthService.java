package com.github.xiaoxixi.gateway.service;

import com.github.xiaoxixi.gateway.result.Result;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

    /**
     * 检查token是否合法
     * @param request
     * @return
     */
    Result<Boolean> checkAccessToken(HttpServletRequest request);
}
