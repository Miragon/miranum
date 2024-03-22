package io.miranum.platform.connect.adapter.in.c8.message;

import io.camunda.zeebe.client.ZeebeClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties
public class Camunda8MessageAutoConfiguration {

    @Bean
    public Camunda8MessageAdapter camunda7MessageAdapter(final ZeebeClient zeebeClient) {
        return new Camunda8MessageAdapter(zeebeClient);
    }

}
