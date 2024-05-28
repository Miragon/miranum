package io.miragon.miranum.connect.camunda7.remote.worker;

import io.miragon.miranum.connect.camnda7.remote.utils.Camunda7PojoMapper;
import io.miragon.miranum.connect.worker.api.WorkerExecuteApi;
import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(Camunda7WorkerProperties.class)
@Configuration
public class Camunda7WorkerAutoConfiguration {

    @Bean
    public Camunda7WorkerAdapter camunda7WorkerAdapter(final ExternalTaskClient externalTaskClient, final WorkerExecuteApi workerExecuteApi, final Camunda7PojoMapper camunda7PojoMapper, final Camunda7WorkerProperties camunda7WorkerProperties) {
        return new Camunda7WorkerAdapter(externalTaskClient, workerExecuteApi, camunda7PojoMapper, camunda7WorkerProperties);
    }
}
