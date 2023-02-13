package io.miragon.miranum.integrations.example.c7;

import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.miragon.miranum.examples.pizzaorder.PizzaOrderConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableZeebeClient
@Import(PizzaOrderConfiguration.class)
public class PizzaOrderC8Application {
    public static void main(final String[] args) {
        SpringApplication.run(PizzaOrderC8Application.class, args);
    }
}