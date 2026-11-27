package com.project.payment_service.service.impl;

import com.project.payment_service.client.RandomNumberClient;
import com.project.payment_service.dto.request.PaymentCreateRequest;
import com.project.payment_service.dto.response.PaymentResponse;
import com.project.payment_service.dto.response.TotalResponse;
import com.project.payment_service.entity.Payment;
import com.project.payment_service.entity.enums.PaymentStatus;
import com.project.payment_service.repository.PaymentRepository;
import com.project.payment_service.service.PaymentService;
import com.project.payment_service.service.mapper.PaymentMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final RandomNumberClient randomNumberClient;

    @Override
    public PaymentResponse createPayment(PaymentCreateRequest request) {

        var payment = paymentMapper.toEntity(request);

        payment.setStatus(resolveStatus());

        var savedPayment = paymentRepository.save(payment);

        return paymentMapper.toDto(savedPayment);
    }

    @Override
    public Page<PaymentResponse> searchPayments(UUID userId, UUID orderId, PaymentStatus status, Pageable pageable) {
        Page<Payment> page = paymentRepository.search(userId, orderId, status, pageable);
        return page.map(paymentMapper::toDto);
    }

    @Override
    public TotalResponse getPaymentsTotalSumForUser(UUID userId, Instant from, Instant to) {
        BigDecimal total = paymentRepository.getTotalSumForUser(userId, from, to);
        return new TotalResponse(total);
    }

    @Override
    public TotalResponse getPaymentsTotalSumForAll(Instant from, Instant to) {
        BigDecimal total = paymentRepository.getTotalSumForAll(from, to);
        return new TotalResponse(total);
    }

    private PaymentStatus resolveStatus() {
        try {
            int number = Integer.parseInt(randomNumberClient.getRandomNumber().trim());
            return number % 2 == 0 ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;
        } catch(FeignException | NumberFormatException ex) {
            log.error(ex.toString());
            return PaymentStatus.FAILED;
        }
    }
}