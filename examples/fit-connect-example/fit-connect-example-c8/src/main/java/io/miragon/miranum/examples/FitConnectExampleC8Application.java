package io.miragon.miranum.examples;

import io.camunda.zeebe.spring.client.EnableZeebeClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableZeebeClient
@EnableScheduling
public class FitConnectExampleC8Application {

    public static void main(String[] args) {
        SpringApplication.run(FitConnectExampleC8Application.class, args);
    }
}