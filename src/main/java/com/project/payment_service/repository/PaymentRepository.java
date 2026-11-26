package com.project.payment_service.repository;

import com.project.payment_service.entity.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends MongoRepository<Payment, UUID>, PaymentRepositoryCustom {
}
