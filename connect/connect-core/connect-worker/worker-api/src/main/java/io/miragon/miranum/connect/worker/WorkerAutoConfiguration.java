package io.miragon.miranum.connect.worker;

import io.miragon.miranum.connect.worker.api.WorkerInterceptor;
import io.miragon.miranum.connect.worker.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;

@Configuration
@Import(ContextInitializer.class)
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

    @Bean
    public BeanPostProcessor workerAnnotationProcessor(WorkerInfoMapper workerInfoMapper, WorkerInfoRegistry workerInfoRegistry) {
        return new WorkerAnnotationBeanPostProcessor(workerInfoMapper, workerInfoRegistry);
    }

    @Bean
    public WorkerInfoBeanPostProcessor workerInfoBeanPostProcessor(WorkerInfoRegistry workerInfoRegistry, WorkerInitializer workerInitializer) {
        return new WorkerInfoBeanPostProcessor(workerInfoRegistry, workerInitializer);
    }
}
