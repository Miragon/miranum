package io.miragon.miranum.integrations.example;

import io.miragon.miranum.connect.binder.worker.application.port.out.ExecuteUseCaseInterceptor;
import io.miragon.miranum.integrations.example.application.port.in.FireAndForgetUseCase;
import io.miragon.miranum.integrations.example.application.port.in.SendMessageUseCase;
import io.miragon.miranum.integrations.example.application.port.out.TenantInterceptor;
import io.miragon.miranum.integrations.example.application.service.FireAndForgetService;
import io.miragon.miranum.integrations.example.application.service.SendMessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExampleConfiguration {

    @Bean
    public SendMessageUseCase sendMessageUseCase() {
        return new SendMessageService();
    }

    @Bean
    public FireAndForgetUseCase fireAndForgetUseCase() {
        return new FireAndForgetService();
    }

    @Bean
    public ExecuteUseCaseInterceptor tenantInterceptor() {
        return new TenantInterceptor();
    }
}
