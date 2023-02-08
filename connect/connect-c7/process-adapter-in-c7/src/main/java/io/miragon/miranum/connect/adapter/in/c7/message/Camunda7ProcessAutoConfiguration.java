package io.miragon.miranum.connect.adapter.in.c7.message;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties
public class Camunda7ProcessAutoConfiguration {

    @Bean
    public Camunda7ProcessAdapter camunda7MessageAdapter() {
        return new Camunda7ProcessAdapter();
    }
}
