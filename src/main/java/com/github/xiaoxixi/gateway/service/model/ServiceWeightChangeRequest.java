package com.github.xiaoxixi.gateway.service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceWeightChangeRequest {

    private String serviceKey;

    private Integer weight;
}
