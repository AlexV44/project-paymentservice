package com.project.payment_service.controller;

import com.project.payment_service.dto.request.PaymentCreateRequest;
import com.project.payment_service.dto.response.PaymentResponse;
import com.project.payment_service.dto.response.TotalResponse;
import com.project.payment_service.entity.enums.PaymentStatus;
import com.project.payment_service.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@Valid @RequestBody PaymentCreateRequest request) {
        PaymentResponse response = paymentService.createPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<PaymentResponse>> searchPayments(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID orderId,
            @RequestParam(required = false) PaymentStatus status,
            @PageableDefault(size = 15, sort = "timestamp") Pageable pageable
    ) {
        Page<PaymentResponse> page = paymentService.searchPayments(userId, orderId, status, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/total/me")
    public ResponseEntity<TotalResponse> getPaymentsTotalSumForUser(
            @RequestParam UUID userId,
            @RequestParam Instant from,
            @RequestParam Instant to
    ) {
        TotalResponse response = paymentService.getPaymentsTotalSumForUser(userId, from, to);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/total")
    public ResponseEntity<TotalResponse> getPaymentsTotalSumForAll(
            @RequestParam Instant from,
            @RequestParam Instant to
    ) {
        TotalResponse response = paymentService.getPaymentsTotalSumForAll(from, to);
        return ResponseEntity.ok(response);
    }
}
