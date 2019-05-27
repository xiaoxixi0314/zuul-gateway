package com.xiaoxixi.gateway.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(builderClassName = "ParamBuilder")
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
