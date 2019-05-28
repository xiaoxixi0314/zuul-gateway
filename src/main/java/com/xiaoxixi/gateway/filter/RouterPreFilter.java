package com.xiaoxixi.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

@Component
public class RouterPreFilter extends ZuulFilter{

    private static final Logger LOGGER = LoggerFactory.getLogger(RouterPreFilter.class);

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
        LOGGER.info("enter pre filter....");
        return null;
    }
}
