package io.miragon.miranum.examples.pizzaorder.waiterc8;

import io.camunda.zeebe.spring.client.EnableZeebeClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableZeebeClient
public class WaiterC8Application {

    public static void main(String[] args) {
        SpringApplication.run(WaiterC8Application.class, args);
    }
}
