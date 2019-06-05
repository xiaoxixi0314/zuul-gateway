package com.github.xiaoxixi.gateway.filter;


import org.junit.Test;

public class RouterFilterTest {

    @Test
    public void createDeployment() {
        String uri = "/api/service/_all";
        int serviceUrl = uri.indexOf("/api");
        System.out.println(serviceUrl);
    }

}
