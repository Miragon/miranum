package io.miragon.miranum.examples.pizzaorder.waiter;

import io.miragon.miranum.examples.pizzaorder.waiter.adapter.in.miranum.MiranumWaiterAdapterAutoConfiguration;
import io.miragon.miranum.examples.pizzaorder.waiter.adapter.in.rest.PlaceOrderControllerAutoConfiguration;
import io.miragon.miranum.examples.pizzaorder.waiter.adapter.out.ProcessAdapterAutoConfiguration;
import io.miragon.miranum.examples.pizzaorder.waiter.application.port.in.IssueCheckUseCase;
import io.miragon.miranum.examples.pizzaorder.waiter.application.port.in.PlaceOrderUseCase;
import io.miragon.miranum.examples.pizzaorder.waiter.application.port.in.ServeDrinksUseCase;
import io.miragon.miranum.examples.pizzaorder.waiter.application.port.in.ServeFoodUseCase;
import io.miragon.miranum.examples.pizzaorder.waiter.application.port.out.PlaceOrderPort;
import io.miragon.miranum.examples.pizzaorder.waiter.application.service.IssueCheckService;
import io.miragon.miranum.examples.pizzaorder.waiter.application.service.PlaceOrderService;
import io.miragon.miranum.examples.pizzaorder.waiter.application.service.ServeDrinksService;
import io.miragon.miranum.examples.pizzaorder.waiter.application.service.ServeFoodService;
import io.miragon.miranum.examples.pizzaorder.waiter.domain.Waiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        MiranumWaiterAdapterAutoConfiguration.class,
        PlaceOrderControllerAutoConfiguration.class,
        ProcessAdapterAutoConfiguration.class
})
public class WaiterAutoConfiguration {

    @Bean
    public Waiter waiter() {
        return new Waiter();
    }

    @Bean
    public IssueCheckUseCase issueCheckUseCase(final Waiter waiter) {
        return new IssueCheckService(waiter);
    }

    @Bean
    public PlaceOrderUseCase placeOrderUseCase(final PlaceOrderPort placeOrderPort) {
        return new PlaceOrderService(placeOrderPort);
    }

    @Bean
    public ServeDrinksUseCase serveDrinksUseCase(final Waiter waiter) {
        return new ServeDrinksService(waiter);
    }

    @Bean
    public ServeFoodUseCase serveFoodUseCase(final Waiter waiter) {
        return new ServeFoodService(waiter);
    }
}
