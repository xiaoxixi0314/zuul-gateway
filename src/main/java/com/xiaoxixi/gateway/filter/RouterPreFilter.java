package com.xiaoxixi.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.xiaoxixi.gateway.result.Result;
import com.xiaoxixi.gateway.service.CurrentLimitingService;
import com.xiaoxixi.gateway.service.DegradeService;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RouterPreFilter extends ZuulFilter{

    private static final Logger LOGGER = LoggerFactory.getLogger(RouterPreFilter.class);

    @Autowired
    private DegradeService degradeService;

    @Autowired
    private CurrentLimitingService currentLimitingService;

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
        if (degradeService.isDegradeRequest(request)) {
            LOGGER.warn("the service was degraded:{}", request.getRequestURI());
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.SC_BAD_GATEWAY);
            Result<Boolean> result = Result.error("502", "service busy");
            context.setResponseBody(JSON.toJSONString(result));
            HttpServletResponse response = context.getResponse();
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        }
        return null;
    }
}
