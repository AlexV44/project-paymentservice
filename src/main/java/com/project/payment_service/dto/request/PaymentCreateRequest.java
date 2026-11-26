package com.project.payment_service.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentCreateRequest(
        @NotNull
        UUID orderId,
        @NotNull
        UUID userId,
        @NotNull
        @Positive
        BigDecimal paymentAmount
) {}
