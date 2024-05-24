package io.miragon.miranum.connect;

import io.miragon.miranum.connect.task.C7TaskApi;
import io.miragon.miranum.connect.task.impl.TaskOutPort;
import org.camunda.community.rest.impl.RemoteTaskService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

public class TaskAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TaskOutPort taskOutPort(final RemoteTaskService remoteTaskService) {
        return new C7TaskApi(remoteTaskService);
    }
}
