package io.miragon.miranum.integrations.mail.adapter.out.mail;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaMailConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "miranum.mail", havingValue = "mail")
    public JavaMailAdapter javaMailAdapter(final JavaMailAdapter javaMailAdapter) {
        return new JavaMailAdapter();
    }

}
