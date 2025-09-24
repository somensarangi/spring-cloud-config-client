package com.example.spring_cloud_config_client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
@RefreshScope
public class MessageController {


    private final ContextRefresher refresher;
    @Value ("${demo.message:default-message}")
    private String message;


    public MessageController(ContextRefresher refresher) {
        this.refresher = refresher;
    }


    @GetMapping ("/message")
    public String message() {
        return message;
    }


    @GetMapping ("/config/check")
    public Object checkAndRefresh() {
        Set<String> changed = refresher.refresh();
        return Map.of("refreshedKeys", changed, "currentMessage", message);
    }
}