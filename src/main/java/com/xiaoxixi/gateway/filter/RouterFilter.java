package com.xiaoxixi.gateway.filter;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.xiaoxixi.service.register.DiscoveryService;
import com.xiaoxixi.service.register.ServiceProperty;
import com.xiaoxixi.service.register.ServiceRegisterConfig;
import okhttp3.*;
import okhttp3.internal.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
//import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@Component
public class RouterFilter extends ZuulFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouterFilter.class);

    private static final  ProxyRequestHelper helper = new ProxyRequestHelper();

    @Autowired
    private DiscoveryService discoveryService;

    @Value("${register.service.prefix}")
    private String registerServicePrefix;

    private static final OkHttpClient httpClient = new OkHttpClient.Builder().build();
    private static final String SERVICE_NAME_HEADER = "X-request-to";

    @Override
    public String filterType() {
        return FilterConstants.ROUTE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SIMPLE_HOST_ROUTING_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
//        return RequestContext.getCurrentContext().getRouteHost() != null
//                && RequestContext.getCurrentContext().sendZuulResponse();
        return true;
    }

    @Override
    public Object run() {
        try {

            RequestContext context = RequestContext.getCurrentContext();
            HttpServletRequest request = context.getRequest();

            String method = request.getMethod();
            String serviceName = request.getHeader(SERVICE_NAME_HEADER);
            String servicePrefix = request.getHeader(registerServicePrefix);
            ServiceProperty service = discoveryService.discoveryService(servicePrefix, serviceName);
            String uri = this.helper.buildZuulRequestURI(request);

            Headers.Builder headers = new Headers.Builder();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                Enumeration<String> values = request.getHeaders(name);

                while (values.hasMoreElements()) {
                    String value = values.nextElement();
                    headers.add(name, value);
                }
            }

            InputStream inputStream = request.getInputStream();

            RequestBody requestBody = null;
            if (inputStream != null && HttpMethod.permitsRequestBody(method)) {
                MediaType mediaType = null;
                if (headers.get("Content-Type") != null) {
                    mediaType = MediaType.parse(headers.get("Content-Type"));
                }
                requestBody = RequestBody.create(mediaType, StreamUtils.copyToByteArray(inputStream));
            }

            Request.Builder builder = new Request.Builder()
                    .headers(headers.build())
                    .url(uri)
                    .method(method, requestBody);

            Response response = httpClient.newCall(builder.build()).execute();

            LinkedMultiValueMap<String, String> responseHeaders = new LinkedMultiValueMap<>();

            for (Map.Entry<String, List<String>> entry : response.headers().toMultimap().entrySet()) {
                responseHeaders.put(entry.getKey(), entry.getValue());
            }

            this.helper.setResponse(response.code(), response.body().byteStream(),
                    responseHeaders);
            context.setRouteHost(null); // prevent SimpleHostRoutingFilter from running

        } catch (IOException ioe) {
            LOGGER.error("route to host io exception:", ioe);
//            this.helper.setResponse("502", "io exception".getBytes(), re);
        }
        return null;
    }
}
