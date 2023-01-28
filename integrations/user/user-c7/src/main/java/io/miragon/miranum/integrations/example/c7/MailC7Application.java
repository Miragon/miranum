package io.miragon.miranum.integrations.example.c7;

import io.miragon.miranum.integrations.mail.MailConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(MailConfiguration.class)
public class MailC7Application {

    public static void main(final String[] args) {
        SpringApplication.run(MailC7Application.class, args);
    }

}
