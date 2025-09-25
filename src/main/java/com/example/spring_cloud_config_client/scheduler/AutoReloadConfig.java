package com.example.spring_cloud_config_client.scheduler;

import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AutoReloadConfig {

  private final ContextRefresher refresher;

  public AutoReloadConfig(ContextRefresher refresher) {
    this.refresher = refresher;
  }

  @Scheduled(fixedDelay = 30000) // every 30 seconds
  public void autoReload() {
    log.info("Auto-reload check started...");
    Set<String> changedKeys = refresher.refresh();
    if (!changedKeys.isEmpty()) {
      log.info("Configuration updated. Changed keys: {}", changedKeys);
    } else {
      log.info("No configuration changes detected.");
    }
  }

}
