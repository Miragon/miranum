package io.miragon.miranum.connect.adapter.in.c8.worker;

import io.camunda.zeebe.client.ZeebeClient;
import io.miragon.miranum.connect.worker.api.WorkerExecuteApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties
@Configuration
public class Camunda8WorkerAutoConfiguration {

    @Bean
    public Camunda8WorkerAdapter camunda8WorkerAdapter(final ZeebeClient zeebeClient, final WorkerExecuteApi workerExecuteApi) {
        return new Camunda8WorkerAdapter(zeebeClient, workerExecuteApi);
    }
}
