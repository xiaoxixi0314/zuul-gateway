package com.github.xiaoxixi.gateway.filter;

import com.github.xiaoxixi.gateway.result.ErrorCodeEnum;
import com.github.xiaoxixi.gateway.result.Result;
import com.github.xiaoxixi.gateway.service.AuthService;
import com.github.xiaoxixi.gateway.service.RequestService;
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

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class RouterPreFilter extends ZuulFilter{

    private static final Logger LOGGER = LoggerFactory.getLogger(RouterPreFilter.class);

    private List<RequestService> validators;

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

        validators.forEach(validator ->{
            if( !validator.validate(context)){
                return;
            }
        });

        return null;
    }

    @PostConstruct
    public void init(){
        validators = new ArrayList<>();
        validators.add(this :: checkAccessToken);
        validators.add(this :: checkDegrade);
        validators.add(this :: checkCurrentLimit);
    }

    public boolean checkAccessToken(RequestContext context) {
        Result<Boolean> authRes = authService.checkAccessToken(context.getRequest());
        if(!authRes.getResult().isSuccess() || !authRes.getData()) {
            ResponseUtils.buildBadResponse(context,
                    HttpStatus.SC_UNAUTHORIZED,
                    authRes.getResult().getErrorCode(),
                    authRes.getResult().getErrorMsg());
            return false;
        }
        return true;
    }

    public boolean checkDegrade(RequestContext context){
        // 降级
        if (degradeService.isDegradeRequest(context.getRequest())) {
            LOGGER.warn("the path was degraded:{}", context.getRequest().getRequestURI());
            ResponseUtils.buildBadResponse(context,
                    HttpStatus.SC_BAD_GATEWAY,
                    ErrorCodeEnum.DEGRADE_REQUEST);
            return false;
        }
        return true;
    }

    public boolean checkCurrentLimit(RequestContext context) {
        // 限流
        if (!currentLimitingService.uriHasRequestToken(context.getRequest())) {
            LOGGER.warn("the path was current limited:{}", context.getRequest().getRequestURI());
            ResponseUtils.buildBadResponse(context,
                    HttpStatus.SC_GATEWAY_TIMEOUT,
                    ErrorCodeEnum.CURRENT_LIMIT_REQUEST);
            return false;
        }
        return true;
    }
}
