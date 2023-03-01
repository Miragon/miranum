package io.miragon.miranum.examples.kitchen.adapter.in;

import io.miragon.miranum.examples.kitchen.application.port.in.PreparePizzaUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MiranumKitchenAdapterAutoConfiguration {

    @Bean
    public MiranumKitchenAdapter miranumKitchenAdapter(final PreparePizzaUseCase preparePizzaUseCase) {
        return new MiranumKitchenAdapter(preparePizzaUseCase);
    }
}