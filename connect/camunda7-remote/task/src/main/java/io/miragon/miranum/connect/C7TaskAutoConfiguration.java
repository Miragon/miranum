package io.miragon.miranum.connect;

import io.miragon.miranum.connect.camnda7.remote.utils.Camunda7RestValueMapper;
import io.miragon.miranum.connect.task.C7TaskApi;
import io.miragon.miranum.connect.task.impl.TaskOutPort;
import org.camunda.community.rest.client.api.TaskApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class C7TaskAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public TaskOutPort taskOutPort(final TaskApi taskApi, final Camunda7RestValueMapper baseVariableMapper) {
        return new C7TaskApi(taskApi, baseVariableMapper);
    }
}
