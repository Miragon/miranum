package io.miragon.miranum.integrations.example.c7;

import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.miragon.miranum.integrations.example.ExampleConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableZeebeClient
@Import(ExampleConfiguration.class)
public class ExampleC8Application {

    public static void main(final String[] args) {
        SpringApplication.run(ExampleC8Application.class, args);
    }

}
