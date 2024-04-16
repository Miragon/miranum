package io.miragon.miranum.platform.connect.worker.worker;

import io.miragon.miranum.platform.connect.worker.worker.api.WorkerInterceptor;
import io.miragon.miranum.platform.connect.worker.worker.api.WorkerSubscription;
import io.miragon.miranum.platform.connect.worker.worker.api.WorkerExecuteApi;
import io.miragon.miranum.platform.connect.worker.worker.api.WorkerRegistry;
import io.miragon.miranum.platform.connect.worker.worker.impl.WorkerAnnotationBeanPostProcessor;
import io.miragon.miranum.platform.connect.worker.worker.impl.WorkerExecuteApiImpl;
import io.miragon.miranum.platform.connect.worker.worker.impl.WorkerRegistryImpl;
import org.springframework.beans.factory.config.BeanPostProcessor;
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
    public WorkerRegistry workerRegistry(final WorkerSubscription subscription) {
        return new WorkerRegistryImpl(subscription);
    }

    @Bean
    public BeanPostProcessor workerAnnotationProcessor(final WorkerRegistry workerRegistry) {
        return new WorkerAnnotationBeanPostProcessor(workerRegistry);
    }
}
