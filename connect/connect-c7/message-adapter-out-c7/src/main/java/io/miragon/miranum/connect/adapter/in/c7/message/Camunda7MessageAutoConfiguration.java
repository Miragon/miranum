package io.miragon.miranum.connect.adapter.in.c7.message;

import org.camunda.community.rest.client.api.MessageApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties
public class Camunda7MessageAutoConfiguration {

    @Bean
    public Camunda7MessageAdapter camunda7MessageAdapter(final MessageApi messageApi) {
        return new Camunda7MessageAdapter(new Camunda7CorrelateMessageMapper(), messageApi);
    }


}
