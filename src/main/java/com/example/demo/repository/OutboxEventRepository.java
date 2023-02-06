package com.example.demo.repository;

import com.example.demo.publisher.OutboxEvent;
import com.example.demo.publisher.OutboxEventProperties;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class OutboxEventRepository {

  @PersistenceContext
  private EntityManager entityManager;

  public List<?> fetch(String entityName) {
    return entityManager.createQuery("select records from " + entityName + " records")
        .getResultList();
  }

  public void delete(Object eventId) {

  }

  public List<OutboxEventProperties> getOutboxEventPropertiesList() {
    return entityManager.getMetamodel().getEntities().stream()
        .filter(eventType -> eventType.getJavaType().isAnnotationPresent(OutboxEvent.class)).map(
            eventType -> OutboxEventProperties.builder().setEventEntityName(eventType.getName())
                .setTopicName(
                    eventType.getJavaType().getAnnotation(OutboxEvent.class).destinationTopicName())
                .build()).collect(Collectors.toList());
  }
}
