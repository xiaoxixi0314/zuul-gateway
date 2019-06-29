package com.github.xiaoxixi.gateway.filter;

import com.github.xiaoxixi.gateway.result.ErrorCodeEnum;
import com.github.xiaoxixi.gateway.result.Result;
import com.github.xiaoxixi.gateway.service.AuthService;
import com.github.xiaoxixi.gateway.util.ResponseUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.github.xiaoxixi.gateway.service.CurrentLimitingService;
import com.github.xiaoxixi.gateway.service.DegradeService;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

@Component
public class RouterPreFilter extends ZuulFilter{

    private static final Logger LOGGER = LoggerFactory.getLogger(RouterPreFilter.class);

    @Autowired
    private DegradeService degradeService;

    @Autowired
    private CurrentLimitingService currentLimitingService;

    @Autowired
    private AuthService authService;

    @Override
    public String filterType(){
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public boolean shouldFilter(){
        return true;
    }

    @Override
    public int filterOrder(){
        return FilterConstants.PRE_DECORATION_FILTER_ORDER;
    }

    @Override
    public Object run(){
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        // check access token
        Result<Boolean> authRes = authService.checkAccessToken(request);
        if(!authRes.getResult().isSuccess() || !authRes.getData()) {
            ResponseUtils.buildBadResponse(context,
                    HttpStatus.SC_UNAUTHORIZED,
                    authRes.getResult().getErrorCode(),
                    authRes.getResult().getErrorMsg());
        }

        // 降级
        if (degradeService.isDegradeRequest(request)) {
            LOGGER.warn("the path was degraded:{}", request.getRequestURI());
            ResponseUtils.buildBadResponse(context,
                    HttpStatus.SC_BAD_GATEWAY,
                    ErrorCodeEnum.DEGRADE_REQUEST);
            return null;
        }

        // 限流
        if (!currentLimitingService.uriHasRequestToken(request)) {
            LOGGER.warn("the path was current limited:{}", request.getRequestURI());
            ResponseUtils.buildBadResponse(context,
                    HttpStatus.SC_GATEWAY_TIMEOUT,
                    ErrorCodeEnum.CURRENT_LIMIT_REQUEST);
            return null;
        }
        return null;
    }
}
