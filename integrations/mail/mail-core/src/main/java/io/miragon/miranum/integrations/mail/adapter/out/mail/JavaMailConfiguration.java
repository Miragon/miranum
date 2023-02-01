package io.miragon.miranum.integrations.mail.adapter.out.mail;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaMailConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "miranum", value = "mail", havingValue = "mail")
    public JavaMailAdapter javaMailAdapter() {
        return new JavaMailAdapter();
    }

}
