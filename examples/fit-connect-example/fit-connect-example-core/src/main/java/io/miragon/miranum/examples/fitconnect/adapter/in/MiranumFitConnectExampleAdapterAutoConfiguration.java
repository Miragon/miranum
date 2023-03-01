package io.miragon.miranum.examples.fitconnect.adapter.in;

import io.miragon.miranum.integrations.mail.application.port.in.SendMailUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MiranumFitConnectExampleAdapterAutoConfiguration {

    @Bean
    public MiranumFitConnectExampleAdapter miranumFitConnectExampleAdapter(final SendMailUseCase sendMailUseCase) {
        return new MiranumFitConnectExampleAdapter(sendMailUseCase);
    }
}