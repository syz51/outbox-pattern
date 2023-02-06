package com.example.demo.model;

import com.example.demo.publisher.OutboxEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
@OutboxEvent(destinationTopicName = "created")
public class PaymentCreatedEvent {

  @Id
  private String eventId;
  private String paymentNumber;

}
