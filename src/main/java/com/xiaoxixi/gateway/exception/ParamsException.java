package com.xiaoxixi.gateway.exception;

public class ParamsException extends RuntimeException {

    public ParamsException() {
        super("discovery service error");
    }

    public ParamsException(String message){
        super(message);
    }

    public ParamsException(String message, Throwable cause) {
        super(message, cause);
    }
}
