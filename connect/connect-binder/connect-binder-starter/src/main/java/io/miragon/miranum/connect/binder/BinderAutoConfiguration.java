package io.miragon.miranum.connect.binder;


import io.miragon.miranum.connect.binder.adapter.in.ContextInitalizer;
import io.miragon.miranum.connect.binder.adapter.in.UseCaseInfoMapper;
import io.miragon.miranum.connect.binder.application.port.in.ExecuteMethodUseCase;
import io.miragon.miranum.connect.binder.application.port.in.InitializeUseCase;
import io.miragon.miranum.connect.binder.application.port.out.BindUseCasePort;
import io.miragon.miranum.connect.binder.application.service.InitalizeUseCasesService;
import io.miragon.miranum.connect.binder.application.service.UseCaseInterceptorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ContextInitalizer.class)
public class BinderAutoConfiguration {

//    @Bean
//    public ContextInitalizer contextInitalizer(final InitializeUseCase initializeUseCase) throws Exception {
//        final ContextInitalizer contextInitalizer = new ContextInitalizer(initializeUseCase, new UseCaseInfoMapper());
//        return contextInitalizer;
//    }

    @Bean
    public UseCaseInfoMapper useCaseInfoMapper() {
        return new UseCaseInfoMapper();
    }

    @Bean
    public ExecuteMethodUseCase executeMethodUseCase() {
        return new UseCaseInterceptorService();
    }

    @Bean
    public InitializeUseCase initalizeUseCasesService(final BindUseCasePort bindUseCasePort) throws Exception {
        return new InitalizeUseCasesService(bindUseCasePort);
    }
}
