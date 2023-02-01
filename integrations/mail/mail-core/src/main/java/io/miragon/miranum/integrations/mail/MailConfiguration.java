package io.miragon.miranum.integrations.mail;

import io.miragon.miranum.integrations.mail.adapter.out.mail.JavaMailConfiguration;
import io.miragon.miranum.integrations.mail.adapter.out.sendgrid.SendGridConfiguration;
import io.miragon.miranum.integrations.mail.application.port.in.SendMailUseCase;
import io.miragon.miranum.integrations.mail.application.port.out.DeliverMailPort;
import io.miragon.miranum.integrations.mail.application.service.SendMailService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(MailProperties.class)
@Import({
        JavaMailConfiguration.class,
        SendGridConfiguration.class
})
public class MailConfiguration {

    @Bean
    public SendMailUseCase sendMailUseCase(final DeliverMailPort deliverMailPort) {
        return new SendMailService(deliverMailPort);
    }
}
