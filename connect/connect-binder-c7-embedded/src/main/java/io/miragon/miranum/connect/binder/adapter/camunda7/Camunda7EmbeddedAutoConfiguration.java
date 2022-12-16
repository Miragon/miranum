package io.miragon.miranum.connect.binder.adapter.camunda7;

import io.miragon.miranum.connect.binder.adapter.camunda7.worker.Camunda7EmbeddedAdapter;
import io.miragon.miranum.connect.binder.worker.application.port.in.ExecuteMethodUseCase;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties
public class Camunda7EmbeddedAutoConfiguration {

    @Bean
    public Camunda7EmbeddedAdapter c8Adapter(final ExecuteMethodUseCase executeMethodUseCase) {
        return new Camunda7EmbeddedAdapter(executeMethodUseCase);
    }

}
