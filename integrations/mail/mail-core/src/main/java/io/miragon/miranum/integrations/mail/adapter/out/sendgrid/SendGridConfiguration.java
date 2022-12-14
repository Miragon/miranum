package io.miragon.miranum.integrations.mail.adapter.out.sendgrid;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendGridConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "miranum.mail", havingValue = "sendgrid")
    public SendGridAdapter javaMailAdapter(final SendGridAdapter javaMailAdapter) {
        return new SendGridAdapter();
    }

}
