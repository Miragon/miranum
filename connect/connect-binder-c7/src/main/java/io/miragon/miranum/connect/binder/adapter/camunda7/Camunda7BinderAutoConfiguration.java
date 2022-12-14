package io.miragon.miranum.connect.binder.adapter.camunda7;

import io.miragon.miranum.connect.binder.application.port.in.ExecuteMethodUseCase;
import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties
public class Camunda7BinderAutoConfiguration {

    @Bean
    public Camunda7Adapter c7Adapter(final ExternalTaskClient externalTaskClient, final ExecuteMethodUseCase executeMethodUseCase) {
        return new Camunda7Adapter(externalTaskClient, new Camunda7Mapper(), executeMethodUseCase);
    }

}
