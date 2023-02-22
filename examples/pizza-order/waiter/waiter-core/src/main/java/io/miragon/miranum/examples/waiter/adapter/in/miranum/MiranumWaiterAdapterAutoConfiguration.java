package io.miragon.miranum.examples.waiter.adapter.in.miranum;

import io.miragon.miranum.examples.waiter.application.port.in.IssueCheckUseCase;
import io.miragon.miranum.examples.waiter.application.port.in.ReassureGuestUseCase;
import io.miragon.miranum.examples.waiter.application.port.in.ServeDrinksUseCase;
import io.miragon.miranum.examples.waiter.application.port.in.ServeFoodUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MiranumWaiterAdapterAutoConfiguration {

    @Bean
    public MiranumWaiterAdapter miranumWaiterAdapter(final ServeDrinksUseCase serveDrinksUseCase,
                                                     final ServeFoodUseCase serveFoodUseCase,
                                                     final IssueCheckUseCase issueCheckUseCase,
                                                     final ReassureGuestUseCase reassureGuestUseCase) {
        return new MiranumWaiterAdapter(serveDrinksUseCase, serveFoodUseCase, issueCheckUseCase, reassureGuestUseCase);
    }
}