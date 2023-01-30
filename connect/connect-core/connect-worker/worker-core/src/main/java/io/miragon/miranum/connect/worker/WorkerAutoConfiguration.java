package io.miragon.miranum.connect.worker;


import io.miragon.miranum.connect.worker.adapter.ContextInitializer;
import io.miragon.miranum.connect.worker.adapter.WorkerInfoMapper;
import io.miragon.miranum.connect.worker.application.port.in.ExecuteMethodUseCase;
import io.miragon.miranum.connect.worker.application.port.in.InitializeWorkerUseCase;
import io.miragon.miranum.connect.worker.application.port.out.BindWorkerPort;
import io.miragon.miranum.connect.worker.application.port.out.WorkerInterceptor;
import io.miragon.miranum.connect.worker.application.service.ExecuteMethodService;
import io.miragon.miranum.connect.worker.application.service.InitializeWorkerService;
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
    public InitializeWorkerUseCase initalizeUseCasesService(final BindWorkerPort bindUseCasePort) throws Exception {
        return new InitializeWorkerService(bindUseCasePort);
    }
}
