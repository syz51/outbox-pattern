package com.example.demo.publisher;

import com.example.demo.repository.OutboxEventRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;

@Slf4j
@Builder(setterPrefix = "set")
public class PublisherTask implements Runnable {

  private final OutboxEventRepository repository;
  private final StreamBridge streamBridge;
  private final OutboxEventProperties eventProperties;

  @Override
  public void run() {
    log.info("Running Outbox Task");
    repository.fetch(eventProperties.getEventEntityName()).forEach(event -> {
      if (streamBridge.send(eventProperties.getTopicName(), event)) {
        repository.delete(event);
      }
    });
  }
}
