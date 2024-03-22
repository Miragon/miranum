package io.miranum.platform.connect.adapter.in.c7.worker;

import io.miranum.platform.connect.c7.utils.Camunda7PojoMapper;
import io.miranum.platform.connect.worker.api.WorkerExecuteApi;
import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(Camunda7WorkerProperties.class)
public class Camunda7WorkerAutoConfiguration {

    @Bean
    public Camunda7WorkerAdapter camunda7WorkerAdapter(final ExternalTaskClient externalTaskClient, final WorkerExecuteApi workerExecuteApi, final Camunda7PojoMapper camunda7PojoMapper, final Camunda7WorkerProperties camunda7WorkerProperties) {
        return new Camunda7WorkerAdapter(externalTaskClient, workerExecuteApi, camunda7PojoMapper, camunda7WorkerProperties);
    }
}
