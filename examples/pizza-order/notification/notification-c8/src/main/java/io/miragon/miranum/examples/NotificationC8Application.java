package io.miragon.miranum.examples;

import io.camunda.zeebe.spring.client.EnableZeebeClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableZeebeClient
public class NotificationC8Application {

    public static void main(String[] args) {
        SpringApplication.run(NotificationC8Application.class, args);
    }
}
