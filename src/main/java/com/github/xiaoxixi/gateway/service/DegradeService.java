package com.github.xiaoxixi.gateway.service;

import com.github.xiaoxixi.gateway.service.model.DegradeRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * degrade service
 */
public interface DegradeService {

    /**
     * 检查请求是否被降级
     * @param request
     * @return
     */
    boolean isDegradeRequest(HttpServletRequest request);

    /**
     * 增加限流请求
     * @param uri
     * @param uriExplain
     */
    void addDegradeRequest(String uri, String uriExplain);

    /**
     * 修改限流请求
     * @param uri
     * @param uriExplain
     */
    void modifyDegradeRequest(String uri, String uriExplain);

    /**
     * 获取所有的限流请求配置
     * @return
     */
    List<DegradeRequest> getAllDegradeRequest();
}
