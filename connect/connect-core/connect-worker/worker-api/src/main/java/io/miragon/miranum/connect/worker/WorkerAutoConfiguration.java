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
    public ExecuteMethodUseCase executeMethodUseCase(final List<WorkerInterceptor> interceptors) {
        return new ExecuteMethodService(interceptors);
    }

    @Bean
    public InitializeWorkerUseCase initializeUseCasesService(final BindWorkerPort bindUseCasePort) {
        return new InitializeWorkerService(bindUseCasePort);
    }
}
