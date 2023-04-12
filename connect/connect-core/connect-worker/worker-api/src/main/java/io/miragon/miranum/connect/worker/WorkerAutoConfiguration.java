package io.miragon.miranum.connect.worker;

import io.miragon.miranum.connect.worker.api.WorkerExecuteApi;
import io.miragon.miranum.connect.worker.api.WorkerInterceptor;
import io.miragon.miranum.connect.worker.api.WorkerRegistry;
import io.miragon.miranum.connect.worker.impl.WorkerExecuteApiImpl;
import io.miragon.miranum.connect.worker.impl.WorkerInitializer;
import io.miragon.miranum.connect.worker.impl.WorkerRegistryImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class WorkerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public WorkerExecuteApi workerExecuteApi(final List<WorkerInterceptor> workerInterceptors) {
        return new WorkerExecuteApiImpl(workerInterceptors);
    }

    @Bean
    @ConditionalOnMissingBean
    public WorkerRegistry workerRegistry() {
        return new WorkerRegistryImpl();
    }

    @Bean
    public WorkerInitializer workerInitializer(final WorkerRegistry workerRegistry) {
        return new WorkerInitializer(workerRegistry);
    }
}
