package com.github.xiaoxixi.gateway.util;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.context.RequestContext;
import com.github.xiaoxixi.gateway.result.ErrorCodeEnum;
import com.github.xiaoxixi.gateway.result.Result;
import org.springframework.http.MediaType;
import javax.servlet.http.HttpServletResponse;

public class ResponseUtils {

    /**
     * 组装错误的返回信息
     * @param context
     * @param statusCode
     * @param error
     */
    public static void buildBadResponse(RequestContext context,
                                        int statusCode,
                                        ErrorCodeEnum error) {
        context.setSendZuulResponse(false);
        context.setResponseStatusCode(statusCode);
        Result<Boolean> result = Result.error(error);
        context.setResponseBody(JSON.toJSONString(result));
        HttpServletResponse response = context.getResponse();
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    }

}
