package com.xiaoxixi.gateway.exception;

public class DiscoveryServiceException extends RuntimeException {

    public DiscoveryServiceException() {
        super("discovery service error");
    }

    public DiscoveryServiceException(String message){
        super(message);
    }

    public DiscoveryServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
