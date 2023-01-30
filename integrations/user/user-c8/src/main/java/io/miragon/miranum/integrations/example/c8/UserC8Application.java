package io.miragon.miranum.integrations.example.c8;

import io.camunda.zeebe.spring.client.EnableZeebeClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import io.miragon.miranum.integrations.user.UserConfiguration;

@SpringBootApplication
@EnableZeebeClient
@Import(UserConfiguration.class)
public class UserC8Application {

    public static void main(final String[] args) {
        SpringApplication.run(UserC8Application.class, args);
    }

}
