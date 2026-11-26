package com.project.payment_service.service.mapper;

import com.project.payment_service.dto.request.PaymentCreateRequest;
import com.project.payment_service.dto.response.PaymentResponse;
import com.project.payment_service.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "timestamp", ignore = true)
    Payment toEntity(PaymentCreateRequest request);

    PaymentResponse toDto(Payment payment);
}
