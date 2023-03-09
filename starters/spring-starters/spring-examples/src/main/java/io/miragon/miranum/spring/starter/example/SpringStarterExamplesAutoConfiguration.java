package io.miragon.miranum.spring.starter.example;

import io.miragon.miranum.connect.cloudevents.CloudEventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringStarterExamplesAutoConfiguration {
    @Bean
    public CloudEventHandler cloudEventExampleHandler() {
        return new CloudEventExampleHandler();
    }
}
