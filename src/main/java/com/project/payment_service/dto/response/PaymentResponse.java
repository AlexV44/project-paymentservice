package com.project.payment_service.dto.response;

import com.project.payment_service.entity.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PaymentResponse(
        UUID id,
        UUID orderId,
        UUID userId,
        PaymentStatus status,
        Instant timestamp,
        BigDecimal paymentAmount
) {}
