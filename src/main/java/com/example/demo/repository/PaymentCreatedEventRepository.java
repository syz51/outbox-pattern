package com.example.demo.repository;

import com.example.demo.model.PaymentCreatedEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentCreatedEventRepository extends JpaRepository<PaymentCreatedEvent, String> {

}
