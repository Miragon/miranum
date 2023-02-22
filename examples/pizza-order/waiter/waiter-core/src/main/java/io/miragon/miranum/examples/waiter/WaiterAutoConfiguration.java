package io.miragon.miranum.examples.waiter;

import io.miragon.miranum.examples.waiter.adapter.in.miranum.MiranumWaiterAdapterAutoConfiguration;
import io.miragon.miranum.examples.waiter.adapter.out.ProcessAdapterAutoConfiguration;
import io.miragon.miranum.examples.waiter.application.port.in.*;
import io.miragon.miranum.examples.waiter.application.port.out.PlaceOrderPort;
import io.miragon.miranum.examples.waiter.application.service.*;
import io.miragon.miranum.examples.waiter.domain.Waiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        MiranumWaiterAdapterAutoConfiguration.class,
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

    @Bean
    public ReassureGuestUseCase reassureGuestUseCase(final Waiter waiter) {
        return new ReassureGuestService(waiter);
    }
}