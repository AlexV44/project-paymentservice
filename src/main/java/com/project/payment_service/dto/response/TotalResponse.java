package com.project.payment_service.dto.response;

import java.math.BigDecimal;

public record TotalResponse(
        BigDecimal total
) {}
