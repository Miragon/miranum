package io.miranum.platform.connect.task;


import io.miranum.platform.connect.task.impl.CompleteTaskPort;
import io.miranum.platform.connect.task.impl.TaskApiImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskAutoConfiguration {

    @Bean
    public TaskApiImpl miranumTaskApi(final CompleteTaskPort completeTaskPort) {
        return new TaskApiImpl(completeTaskPort);
    }
}

