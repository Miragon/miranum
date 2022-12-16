package io.miragon.miranum.connect.binder.adapter.spring;


import io.miragon.miranum.connect.binder.adapter.spring.worker.ContextInitalizer;
import io.miragon.miranum.connect.binder.adapter.spring.worker.WorkerInfoMapper;
import io.miragon.miranum.connect.binder.worker.application.port.in.ExecuteMethodUseCase;
import io.miragon.miranum.connect.binder.worker.application.port.in.InitializeUseCase;
import io.miragon.miranum.connect.binder.worker.application.port.out.BindWorkerPort;
import io.miragon.miranum.connect.binder.worker.application.port.out.ExecuteUseCaseInterceptor;
import io.miragon.miranum.connect.binder.worker.application.service.ExecuteMethodService;
import io.miragon.miranum.connect.binder.worker.application.service.InitalizeWorkerService;
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
