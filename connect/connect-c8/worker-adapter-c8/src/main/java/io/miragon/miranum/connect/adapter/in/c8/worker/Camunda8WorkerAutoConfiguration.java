package io.miragon.miranum.connect.adapter.in.c8.worker;

import io.camunda.zeebe.client.ZeebeClient;
import io.miragon.miranum.connect.worker.impl.ExecuteMethodUseCase;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties
public class Camunda8WorkerAutoConfiguration {

    @Bean
    public Camunda8WorkerAdapter camunda8WorkerAdapter(final ZeebeClient zeebeClient, final ExecuteMethodUseCase executeMethodUseCase) {
        return new Camunda8WorkerAdapter(zeebeClient, executeMethodUseCase);
    }

}
