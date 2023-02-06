package com.example.demo.publisher;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "oneap.event.publisher")
public class PublisherProperties {

  private int threadPoolSize = 1;
  private long taskIntervalInSeconds = 1L;
}
