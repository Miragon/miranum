package io.miragon.miranum.starter.spring.web.c7;

import io.miragon.miranum.connect.cloudevents.CloudEventsAutoConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(CloudEventsAutoConfiguration.class)
public class GatewayC7Application {

    public static void main(final String[] args) {
        SpringApplication.run(GatewayC7Application.class, args);
    }
}
