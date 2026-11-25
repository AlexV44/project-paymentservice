package com.project.payment_service.repository;

import java.math.BigDecimal;

public record PaymentTotal(
        BigDecimal total
) {}
