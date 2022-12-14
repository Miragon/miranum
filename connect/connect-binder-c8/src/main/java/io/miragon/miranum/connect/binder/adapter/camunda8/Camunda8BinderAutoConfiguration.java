package io.miragon.miranum.connect.binder.adapter.camunda8;

import io.camunda.zeebe.client.ZeebeClient;
import io.miragon.miranum.connect.binder.application.port.in.ExecuteMethodUseCase;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties
public class Camunda8BinderAutoConfiguration {

    @Bean
    public Camunda8Adapter c8Adapter(final ZeebeClient zeebeClient, final ExecuteMethodUseCase executeMethodUseCase) {
        return new Camunda8Adapter(zeebeClient, executeMethodUseCase);
    }

}
