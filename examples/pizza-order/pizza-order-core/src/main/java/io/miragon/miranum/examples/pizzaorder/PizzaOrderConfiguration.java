package io.miragon.miranum.examples.pizzaorder;

import io.miragon.miranum.connect.message.api.MessageApi;
import io.miragon.miranum.examples.pizzaorder.application.port.in.*;
import io.miragon.miranum.examples.pizzaorder.application.service.*;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class PizzaOrderConfiguration {

    @Bean
    ServeDrinksUseCase deliverDrinksUseCase() {
        return new ServeDrinksService();
    }

    @Bean
    IssueCheckUseCase issueCheckUseCase() {
        return new IssueCheckService();
    }

    @Bean
    PreparePizzaUseCase preparePizzaUseCase(final MessageApi messageApi) {
        return new PreparePizzaService(messageApi);
    }

    @Bean
    RegisterOrderUseCase registerOrderUseCase() {
        return new RegisterOrderService();
    }

    @Bean
    ServePizzaUseCase servePizzaUseCase() {
        return new ServePizzaService();
    }
}
