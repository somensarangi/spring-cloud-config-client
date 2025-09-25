package com.example.spring_cloud_config_client.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class MessageController {


    private final ContextRefresher refresher;
    @Value ("${demo.message:default-message}")
    private String message;

    @Autowired
    private ConfigurableEnvironment env;


    public MessageController(ContextRefresher refresher) {
        this.refresher = refresher;
    }

    @GetMapping("/print-all-config")
    public Map<String, String> printAllConfig() {
        Map<String, String> result = new LinkedHashMap<>();

        for (PropertySource<?> propertySource : env.getPropertySources()) {
            String sourceName = propertySource.getName();

            // Only include property sources coming from Spring Cloud Config (usually named "configService:...")
            if (sourceName != null && sourceName.startsWith("configserver:")) {
                Object source = propertySource.getSource();
                if (source instanceof Map) {
                    Map<?, ?> map = (Map<?, ?>) source;
                    for (Map.Entry<?, ?> entry : map.entrySet()) {
                        Object key = entry.getKey();
                        Object value = entry.getValue();
                        if (key instanceof String && value != null) {
                            result.put((String) key, String.valueOf(value));
                        }
                    }
                }
            }
        }

        return result;
    }

    @GetMapping("/print-config")
    public String printConfig() {
        return "Printing Config fetched from Server = " + message;
    }

}