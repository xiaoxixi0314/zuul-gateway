package com.github.xiaoxixi.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Index {

    @GetMapping("")
    public String index(){
        return "zuul gateway works";
    }
}
