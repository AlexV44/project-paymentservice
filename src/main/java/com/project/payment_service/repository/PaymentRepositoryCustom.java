package com.project.payment_service.repository;

import com.project.payment_service.entity.Payment;
import com.project.payment_service.entity.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public interface PaymentRepositoryCustom {

    Page<Payment> search(UUID userId, UUID orderId, PaymentStatus status, Pageable pageable);
    BigDecimal getTotalSumForUser(UUID userId, Instant from, Instant to);
    BigDecimal getTotalSumForAll(Instant from, Instant to);
}
