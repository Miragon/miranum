package io.miragon.miranum.examples.pizzaorder.emailc8;

import io.camunda.zeebe.spring.client.EnableZeebeClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableZeebeClient
public class EmailC8Application {

    public static void main(String[] args) {
        SpringApplication.run(EmailC8Application.class, args);
    }
}
