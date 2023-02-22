package io.miragon.miranum.examples.kitchen.adapter.in.miranum;

import io.miragon.miranum.connect.message.api.MessageApi;
import io.miragon.miranum.examples.kitchen.application.port.in.PreparePizzaUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MiranumKitchenAdapterAutoConfiguration {

    @Bean
    public MiranumKitchenAdapter miranumKitchenAdapter(final PreparePizzaUseCase preparePizzaUseCase, final MessageApi messageApi) {
        return new MiranumKitchenAdapter(preparePizzaUseCase, messageApi);
    }
}