package io.miragon.miranum.connect.worker;

import io.miragon.miranum.connect.worker.api.WorkerInterceptor;
import io.miragon.miranum.connect.worker.impl.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class WorkerAutoConfiguration {

    @Bean
    public WorkerInfoMapper useCaseInfoMapper() {
        return new WorkerInfoMapper();
    }

    @Bean
    public MethodExecutor executeMethodUseCase(final List<WorkerInterceptor> interceptors) {
        return new MethodExecutor(interceptors);
    }

    @Bean
    public WorkerInitializer initializeUseCasesService(final BindWorkerPort bindUseCasePort) {
        return new WorkerInitializer(bindUseCasePort);
    }

    @Bean
    public WorkerInfoRegistry workerInfoRegistry() {
        return new WorkerInfoRegistry();
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