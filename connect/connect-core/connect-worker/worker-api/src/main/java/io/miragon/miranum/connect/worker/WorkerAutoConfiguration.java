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
}
