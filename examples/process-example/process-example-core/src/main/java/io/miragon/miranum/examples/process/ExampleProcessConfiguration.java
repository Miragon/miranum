package io.miragon.miranum.examples.process;

import io.miragon.miranum.examples.process.application.port.in.SendMessageUseCase;
import io.miragon.miranum.examples.process.application.service.SendMessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExampleProcessConfiguration {

    @Bean
    public SendMessageUseCase sendMessageUseCase() {
        return new SendMessageService();
    }
}
