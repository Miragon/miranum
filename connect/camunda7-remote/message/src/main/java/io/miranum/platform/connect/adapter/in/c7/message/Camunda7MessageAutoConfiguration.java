package io.miranum.platform.connect.adapter.in.c7.message;

import io.miranum.platform.connect.c7.utils.Camunda7RestValueMapper;
import org.camunda.community.rest.client.api.MessageApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties
public class Camunda7MessageAutoConfiguration {

    @Bean
    public Camunda7MessageAdapter camunda7MessageAdapter(final MessageApi messageApi, final Camunda7RestValueMapper camunda7BaseVariableValueMapper) {
        return new Camunda7MessageAdapter(new Camunda7CorrelateMessageMapper(camunda7BaseVariableValueMapper), messageApi);
    }
}
