package io.miragon.miranum.connect.binder.adapter.camunda7
        ;

import io.miragon.miranum.connect.binder.adapter.camunda7.common.Camunda7Mapper;
import io.miragon.miranum.connect.binder.adapter.camunda7.message.Camunda7CorrelateMessageMapper;
import io.miragon.miranum.connect.binder.adapter.camunda7.message.Camunda7MessageAdapter;
import io.miragon.miranum.connect.binder.adapter.camunda7.worker.Camunda7WorkerAdapter;
import io.miragon.miranum.connect.binder.worker.application.port.in.ExecuteMethodUseCase;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.community.rest.client.api.MessageApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties
public class Camunda7BinderAutoConfiguration {

    @Bean
    public Camunda7WorkerAdapter camunda7WorkerAdapter(final ExternalTaskClient externalTaskClient, final ExecuteMethodUseCase executeMethodUseCase) {
        return new Camunda7WorkerAdapter(externalTaskClient, new Camunda7Mapper(), executeMethodUseCase);
    }

    @Bean
    public Camunda7MessageAdapter camunda7MessageAdapter(final MessageApi messageApi) {
        return new Camunda7MessageAdapter(new Camunda7CorrelateMessageMapper(), messageApi);
    }

}
