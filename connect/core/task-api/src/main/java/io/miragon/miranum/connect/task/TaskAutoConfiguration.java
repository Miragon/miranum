package io.miragon.miranum.connect.task;


import io.miragon.miranum.connect.task.impl.TaskApiImpl;
import io.miragon.miranum.connect.task.impl.TaskOutPort;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TaskApiImpl miranumTaskApi(final TaskOutPort taskOutPort) {
        return new TaskApiImpl(taskOutPort);
    }
}
