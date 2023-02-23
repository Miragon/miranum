package io.miragon.miranum.integrations.mail.adapter.in.miranum;

import io.miragon.miranum.integrations.mail.application.port.in.SendMailUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MiranumEmailAdapterConfiguration {

    @Bean
    public MiranumEmailAdapter miranumEmailAdapter(final SendMailUseCase sendMailUseCase) {
        return new MiranumEmailAdapter(sendMailUseCase);
    }
}
