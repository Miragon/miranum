package io.miragon.miranum.examples.process.c8;

import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.miragon.miranum.examples.process.ProcessExampleConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableZeebeClient
@Import(ProcessExampleConfiguration.class)
public class ProcessExampleC8Application {

    public static void main(final String[] args) {
        SpringApplication.run(ProcessExampleC8Application.class, args);
    }

}
