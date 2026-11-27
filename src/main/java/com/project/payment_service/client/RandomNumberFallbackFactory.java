package com.project.payment_service.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RandomNumberFallbackFactory implements FallbackFactory<RandomNumberClient> {

    private static final String FALLBACK_NUMBER = "1";

    @Override
    public RandomNumberClient create(Throwable cause) {
        return () -> {
            log.error(cause.toString());
            return FALLBACK_NUMBER;
        };
    }
}
