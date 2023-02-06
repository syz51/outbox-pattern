package com.example.demo.publisher;

import com.example.demo.repository.OutboxEventRepository;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.task.TaskSchedulerBuilder;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;

@Slf4j
@Configuration
@ConfigurationPropertiesScan
@AllArgsConstructor
public class PublisherConfiguration {

  private PublisherProperties publisherProperties;

  @Bean
  public TaskScheduler taskScheduler(TaskSchedulerBuilder taskSchedulerBuilder) {
    return taskSchedulerBuilder.poolSize(publisherProperties.getThreadPoolSize()).build();
  }

  @Bean
  public List<ScheduledFuture<?>> scheduleTasks(TaskScheduler taskScheduler,
      OutboxEventRepository outboxEventRepository, StreamBridge streamBridge) {
    return outboxEventRepository.getOutboxEventPropertiesList().stream().map(
            publisherTaskDefinition -> taskScheduler.scheduleWithFixedDelay(
                PublisherTask.builder().setEventProperties(publisherTaskDefinition)
                    .setRepository(outboxEventRepository).setStreamBridge(streamBridge).build(),
                Duration.ofSeconds(publisherProperties.getTaskIntervalInSeconds())))
        .collect(Collectors.toList());
  }

}
