package io.miragon.miranum.integrations.example;

import io.miragon.miranum.integrations.example.application.port.in.SendMessageUseCase;
import io.miragon.miranum.integrations.example.application.service.SendMessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExampleConfiguration {

    @Bean
    public SendMessageUseCase sendMessageUseCase() {
        return new SendMessageService();
    }

}
