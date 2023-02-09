package io.miragon.miranum.connect.adapter.in.c8.process;

import io.camunda.zeebe.client.ZeebeClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties
public class Camunda8ProcessAutoConfiguration {

    @Bean
    public Camunda8ProcessAdapter camunda7MessageAdapter(final ZeebeClient zeebeClient) {
        return new Camunda8ProcessAdapter(zeebeClient);
    }
}
