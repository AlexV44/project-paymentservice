package com.project.payment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "random-number-client",
        url = "${app.external-api.random-url}",
        fallbackFactory = RandomNumberFallbackFactory.class
)
public interface RandomNumberClient {

    @GetMapping("/integers/?num=1&min=1&max=100&col=1&base=10&format=plain&rnd=new")
    String getRandomNumber();
}
