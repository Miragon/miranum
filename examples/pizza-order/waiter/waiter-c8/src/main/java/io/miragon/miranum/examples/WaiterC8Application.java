package io.miragon.miranum.examples;

import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableZeebeClient
@Deployment(resources = "classpath*:**/pizza-order-c8.bpmn")
public class WaiterC8Application {

    public static void main(String[] args) {
        SpringApplication.run(WaiterC8Application.class, args);
    }
}