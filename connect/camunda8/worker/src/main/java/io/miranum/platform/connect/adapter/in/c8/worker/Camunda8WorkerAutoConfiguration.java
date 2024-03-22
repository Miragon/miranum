package io.miranum.platform.connect.adapter.in.c8.worker;

import io.camunda.zeebe.client.ZeebeClient;
import io.miranum.platform.connect.worker.api.WorkerExecuteApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties
public class Camunda8WorkerAutoConfiguration {

    @Bean
    public Camunda8WorkerAdapter camunda8WorkerAdapter(final ZeebeClient zeebeClient, final WorkerExecuteApi workerExecuteApi) {
        return new Camunda8WorkerAdapter(zeebeClient, workerExecuteApi);
    }
}