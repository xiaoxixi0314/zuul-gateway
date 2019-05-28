package com.xiaoxixi.gateway.service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestTokenConfig {

    /**
     * current limited uri
     */
    private String uri;

    /**
     * uri explain
     */
    private String uriExplain;

    /**
     * in unit time, allow request's count
     */
    private Integer tokenCount;

    /**
     * unit time, unit:s
     */
    private Integer unitTime;
}
