package io.miranum.platform.connect.worker;

import io.miranum.platform.connect.worker.api.WorkerExecuteApi;
import io.miranum.platform.connect.worker.api.WorkerInterceptor;
import io.miranum.platform.connect.worker.api.WorkerRegistry;
import io.miranum.platform.connect.worker.api.WorkerSubscription;
import io.miranum.platform.connect.worker.impl.WorkerAnnotationBeanPostProcessor;
import io.miranum.platform.connect.worker.impl.WorkerExecuteApiImpl;
import io.miranum.platform.connect.worker.impl.WorkerRegistryImpl;
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