package com.example.demo.publisher;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "set")
public class OutboxEventProperties {

  private String topicName;
  private String eventEntityName;
}
