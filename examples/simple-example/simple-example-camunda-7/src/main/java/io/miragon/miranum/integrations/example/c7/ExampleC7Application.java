package io.miragon.miranum.integrations.example.c7;

import io.miragon.miranum.integrations.example.ExampleConfiguration;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ExampleConfiguration.class)
@AllArgsConstructor
public class ExampleC7Application {

    private final ExternalTaskClient client;

    public static void main(final String[] args) {
        SpringApplication.run(ExampleC7Application.class, args);
    }

    @PostConstruct
    public void init() {
        this.client.isActive();
    }
}