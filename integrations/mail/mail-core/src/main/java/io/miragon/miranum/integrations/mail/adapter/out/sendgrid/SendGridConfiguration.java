package io.miragon.miranum.integrations.mail.adapter.out.sendgrid;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendGridConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "miranum", value = "mail", havingValue = "sendgrid", matchIfMissing = true)
    public SendGridAdapter sendGridAdapter() {
        return new SendGridAdapter();
    }
}
