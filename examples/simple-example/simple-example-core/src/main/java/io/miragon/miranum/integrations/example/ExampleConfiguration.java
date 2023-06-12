package io.miragon.miranum.integrations.example;

import io.miragon.miranum.connect.message.api.MessageApi;
import io.miragon.miranum.connect.worker.api.WorkerInterceptor;
import io.miragon.miranum.integrations.example.adapter.out.DataBaseAdapter;
import io.miragon.miranum.integrations.example.adapter.out.DataRepository;
import io.miragon.miranum.integrations.example.application.port.in.FireAndForgetUseCase;
import io.miragon.miranum.integrations.example.application.port.in.SendMessageUseCase;
import io.miragon.miranum.integrations.example.application.port.out.DataQuery;
import io.miragon.miranum.integrations.example.application.port.out.TenantInterceptor;
import io.miragon.miranum.integrations.example.application.service.FireAndForgetService;
import io.miragon.miranum.integrations.example.application.service.SendMessageService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class ExampleConfiguration {

    private final DataRepository dataRepository;

    @Bean
    public DataQuery dataQuery() {
        return new DataBaseAdapter(dataRepository);
    }

    @Bean
    public SendMessageUseCase sendMessageUseCase(final MessageApi messageApi) {
        return new SendMessageService(messageApi, dataQuery());
    }

    @Bean
    public FireAndForgetUseCase fireAndForgetUseCase() {
        return new FireAndForgetService();
    }

    @Bean
    public WorkerInterceptor tenantInterceptor() {
        return new TenantInterceptor();
    }
}
