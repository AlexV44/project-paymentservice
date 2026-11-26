package com.project.payment_service.service;

import com.project.payment_service.dto.request.PaymentCreateRequest;
import com.project.payment_service.dto.response.PaymentResponse;
import com.project.payment_service.dto.response.TotalResponse;
import com.project.payment_service.entity.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.UUID;

public interface PaymentService {
    PaymentResponse createPayment(PaymentCreateRequest request);
    Page<PaymentResponse> searchPayments(UUID userId, UUID orderId, PaymentStatus status, Pageable pageable);
    TotalResponse getPaymentsTotalSumForUser(UUID userId, Instant from, Instant to);
    TotalResponse getPaymentsTotalSumForAll(Instant from, Instant to);
}
