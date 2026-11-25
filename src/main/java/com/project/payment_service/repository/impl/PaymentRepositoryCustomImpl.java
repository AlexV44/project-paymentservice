package com.project.payment_service.repository.impl;

import com.project.payment_service.entity.Payment;
import com.project.payment_service.entity.enums.PaymentStatus;
import com.project.payment_service.repository.PaymentRepositoryCustom;
import com.project.payment_service.repository.PaymentTotal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class PaymentRepositoryCustomImpl implements PaymentRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<Payment> search(UUID userId, UUID orderId, PaymentStatus status, Pageable pageable) {
        List<Criteria> filters = new ArrayList<>();
        if(userId != null) {
            filters.add(Criteria.where("userId").is(userId));
        }
        if(orderId != null) {
            filters.add(Criteria.where("orderId").is(orderId));
        }
        if(status != null) {
            filters.add(Criteria.where("status").is(status));
        }

        Query filterQuery = filters.isEmpty()
                ? new Query()
                : new Query(new Criteria().andOperator(filters));

        long total = mongoTemplate.count(filterQuery, Payment.class);
        List<Payment> content = mongoTemplate.find(Query.of(filterQuery).with(pageable), Payment.class);

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public BigDecimal getTotalSumForUser(UUID userId, Instant from, Instant to) {
        if(userId == null) {
            throw new NullPointerException();
        }
        return sum(Criteria.where("userId").is(userId), from, to);
    }

    @Override
    public BigDecimal getTotalSumForAll(Instant from, Instant to) {
        return sum(null, from, to);
    }

    private BigDecimal sum(Criteria userFilter, Instant from, Instant to) {
        List<Criteria> filters = new ArrayList<>();

        filters.add(Criteria.where("status").is(PaymentStatus.SUCCESS));
        filters.add(Criteria.where("timestamp").gte(from).lte(to));

        if(userFilter != null) {
            filters.add(userFilter);
        }

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(new Criteria().andOperator(filters)),
                Aggregation.group().sum("paymentAmount").as("total")
        );

        PaymentTotal result = mongoTemplate.aggregate(aggregation, Payment.class, PaymentTotal.class).getUniqueMappedResult();
        return result == null || result.total() == null ? BigDecimal.ZERO : result.total();
    }
}
