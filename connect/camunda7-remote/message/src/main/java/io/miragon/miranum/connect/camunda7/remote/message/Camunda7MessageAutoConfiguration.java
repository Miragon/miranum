package io.miragon.miranum.connect.camunda7.remote.message;

import io.miragon.miranum.connect.camnda7.remote.utils.Camunda7RestValueMapper;
import org.camunda.community.rest.client.api.MessageApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties
@Configuration
public class Camunda7MessageAutoConfiguration {

    @Bean
    public Camunda7MessageAdapter camunda7MessageAdapter(final MessageApi messageApi, final Camunda7RestValueMapper camunda7BaseVariableValueMapper) {
        return new Camunda7MessageAdapter(new Camunda7CorrelateMessageMapper(camunda7BaseVariableValueMapper), messageApi);
    }
}
