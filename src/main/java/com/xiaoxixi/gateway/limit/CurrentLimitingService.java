package com.xiaoxixi.gateway.limit;

import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;

/**
 * current limiting service implement
 */
@Service
public interface CurrentLimitingService {

    boolean hasRequstToken(HttpServletRequest request);
}
