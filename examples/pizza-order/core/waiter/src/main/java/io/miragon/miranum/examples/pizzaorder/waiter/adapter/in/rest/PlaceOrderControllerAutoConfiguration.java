package io.miragon.miranum.examples.pizzaorder.waiter.adapter.in.rest;

import io.miragon.miranum.examples.pizzaorder.waiter.application.port.in.PlaceOrderUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlaceOrderControllerAutoConfiguration {

    @Bean
    public PlaceOrderController placeOrderController(final PlaceOrderUseCase placeOrderUseCase) {
        return new PlaceOrderController(placeOrderUseCase);
    }
}
