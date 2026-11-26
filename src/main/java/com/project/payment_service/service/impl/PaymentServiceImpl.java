package com.project.payment_service.service.impl;

import com.project.payment_service.dto.request.PaymentCreateRequest;
import com.project.payment_service.dto.response.PaymentResponse;
import com.project.payment_service.dto.response.TotalResponse;
import com.project.payment_service.entity.enums.PaymentStatus;
import com.project.payment_service.service.PaymentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public PaymentResponse createPayment(PaymentCreateRequest request) {
        return null;
    }

    @Override
    public Page<PaymentResponse> searchPayments(UUID userId, UUID orderId, PaymentStatus status, Pageable pageable) {
        return null;
    }

    @Override
    public TotalResponse getPaymentsTotalSumForUser(UUID userId, Instant from, Instant to) {
        return null;
    }

    @Override
    public TotalResponse getPaymentsTotalSumForAll(Instant from, Instant to) {
        return null;
    }
}
