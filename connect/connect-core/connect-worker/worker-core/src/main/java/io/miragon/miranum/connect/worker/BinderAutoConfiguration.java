package io.miragon.miranum.connect.worker;


import io.miragon.miranum.connect.worker.adapter.ContextInitalizer;
import io.miragon.miranum.connect.worker.adapter.WorkerInfoMapper;
import io.miragon.miranum.connect.worker.application.port.in.ExecuteMethodUseCase;
import io.miragon.miranum.connect.worker.application.port.in.InitializeUseCase;
import io.miragon.miranum.connect.worker.application.port.out.BindWorkerPort;
import io.miragon.miranum.connect.worker.application.port.out.ExecuteUseCaseInterceptor;
import io.miragon.miranum.connect.worker.application.service.ExecuteMethodService;
import io.miragon.miranum.connect.worker.application.service.InitalizeWorkerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;

@Configuration
@Import(ContextInitalizer.class)
public class BinderAutoConfiguration {

    @Bean
    public WorkerInfoMapper useCaseInfoMapper() {
        return new WorkerInfoMapper();
    }

    @Bean
    public ExecuteMethodUseCase executeMethodUseCase(final List<ExecuteUseCaseInterceptor> interceptors) {
        return new ExecuteMethodService(interceptors);
    }

    @Bean
    public InitializeUseCase initalizeUseCasesService(final BindWorkerPort bindUseCasePort) throws Exception {
        return new InitalizeWorkerService(bindUseCasePort);
    }
}
