package io.miragon.miranum.examples.pizzaorder.kitchen;

import io.miragon.miranum.examples.pizzaorder.kitchen.adapter.in.miranum.MiranumKitchenAdapterAutoConfiguration;
import io.miragon.miranum.examples.pizzaorder.kitchen.application.port.in.PreparePizzaUseCase;
import io.miragon.miranum.examples.pizzaorder.kitchen.application.service.PreparePizzaService;
import io.miragon.miranum.examples.pizzaorder.kitchen.domain.Kitchen;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MiranumKitchenAdapterAutoConfiguration.class)
public class KitchenAutoConfiguration {

    @Bean
    public Kitchen kitchen() {
        return new Kitchen();
    }

    @Bean
    public PreparePizzaUseCase preparePizzaUseCase(final Kitchen kitchen) {
        return new PreparePizzaService(kitchen);
    }
}
