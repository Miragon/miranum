package io.miragon.miranum.examples.pizzaorder;

import io.miragon.miranum.connect.message.application.port.in.CorrelateMessageUseCase;
import io.miragon.miranum.examples.pizzaorder.application.port.in.DeliverDrinksUseCase;
import io.miragon.miranum.examples.pizzaorder.application.port.in.IssueCheckUseCase;
import io.miragon.miranum.examples.pizzaorder.application.port.in.PreparePizzaUseCase;
import io.miragon.miranum.examples.pizzaorder.application.port.in.RegisterOrderUseCase;
import io.miragon.miranum.examples.pizzaorder.application.service.DeliverDrinksService;
import io.miragon.miranum.examples.pizzaorder.application.service.IssueCheckService;
import io.miragon.miranum.examples.pizzaorder.application.service.PreparePizzaService;
import io.miragon.miranum.examples.pizzaorder.application.service.RegisterOrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PizzaOrderConfiguration {

    @Bean
    DeliverDrinksUseCase deliverDrinksUseCase() {
        return new DeliverDrinksService();
    }

    @Bean
    IssueCheckUseCase issueCheckUseCase() {
        return new IssueCheckService();
    }

    @Bean
    PreparePizzaUseCase preparePizzaUseCase(final CorrelateMessageUseCase correlateMessageUseCase) {
        return new PreparePizzaService(correlateMessageUseCase);
    }

    @Bean
    RegisterOrderUseCase registerOrderUseCase() {
        return new RegisterOrderService();
    }
}
