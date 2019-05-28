package com.xiaoxixi.gateway.service;

import com.xiaoxixi.gateway.service.model.DegradeRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * degrade service
 */
public interface DegradeService {

    boolean isDegradeRequest(HttpServletRequest request);

    void addDegradeRequest(String uri, String uriExplain);

    void modifyDegradeRequest(String uri, String uriExplain);

    List<DegradeRequest> getAllDegradeRequest();
}
