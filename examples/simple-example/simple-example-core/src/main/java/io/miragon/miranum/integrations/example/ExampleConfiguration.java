package io.miragon.miranum.integrations.example;

import io.miragon.miranum.connect.message.application.port.in.CorrelateMessageUseCase;
import io.miragon.miranum.connect.worker.application.port.out.WorkerInterceptor;
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
    public SendMessageUseCase sendMessageUseCase(CorrelateMessageUseCase correlateMessageUseCase) {
        return new SendMessageService(correlateMessageUseCase);
    }

    @Bean
    public FireAndForgetUseCase fireAndForgetUseCase() {
        return new FireAndForgetService();
    }

    @Bean
    public WorkerInterceptor tenantInterceptor() {
        return new TenantInterceptor();
    }
}
