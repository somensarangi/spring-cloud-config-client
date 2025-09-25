package com.example.spring_cloud_config_client.controller;

import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ReloadController {

  private final ContextRefresher refresher;

  public ReloadController(ContextRefresher refresher) {
    this.refresher = refresher;
  }

  @PostMapping("/reload-config")
  public Set<String> reloadConfig() {
    // returns the set of keys that changed
    log.info("Reloading configuration...");
    return refresher.refresh();
  }
}

