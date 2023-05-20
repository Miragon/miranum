package io.miranum.platform.tasklist;

import io.holunda.polyflow.view.TaskQueryClient;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class TasklistConfiguration {

    @Bean
    public TaskQueryClient taskQueryClient(QueryGateway queryGateway) {
        return new TaskQueryClient(queryGateway);
    }
}
