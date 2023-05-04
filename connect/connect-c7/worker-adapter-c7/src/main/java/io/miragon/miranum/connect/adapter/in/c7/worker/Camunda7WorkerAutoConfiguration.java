package io.miragon.miranum.connect.adapter.in.c7.worker;

import io.miragon.miranum.connect.c7.utils.Camunda7PojoMapper;
import io.miragon.miranum.connect.worker.api.WorkerExecuteApi;
import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties
public class Camunda7WorkerAutoConfiguration {

    @Bean
    public Camunda7WorkerAdapter camunda7WorkerAdapter(final ExternalTaskClient externalTaskClient, final WorkerExecuteApi workerExecuteApi, final Camunda7PojoMapper camunda7PojoMapper) {
        return new Camunda7WorkerAdapter(externalTaskClient, workerExecuteApi, camunda7PojoMapper);
    }
}
