package io.miragon.miranum.integrations.example.c7;

import io.miragon.miranum.integrations.user.UserConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(UserConfiguration.class)
public class UserC7Application {

    public static void main(final String[] args) {
        SpringApplication.run(UserC7Application.class, args);
    }
}
