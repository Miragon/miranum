package io.miragon.miranum.examples.process;

import io.miragon.miranum.connect.process.impl.StartProcessPort;
import io.miragon.miranum.examples.process.adapter.in.rest.StartProcessController;
import io.miragon.miranum.examples.process.application.port.in.SendMessageUseCase;
import io.miragon.miranum.examples.process.application.service.SendMessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessExampleConfiguration {

    @Bean
    public SendMessageUseCase sendMessageUseCase() {
        return new SendMessageService();
    }

    @Bean
    public StartProcessController startProcessController(final StartProcessPort startProcessPort) {
        return new StartProcessController(startProcessPort);
    }
}
