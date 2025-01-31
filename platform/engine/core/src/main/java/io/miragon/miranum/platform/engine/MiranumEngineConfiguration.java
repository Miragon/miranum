package io.miragon.miranum.platform.engine;

import io.miragon.miranum.platform.engine.adapter.out.schema.SchemaClient;
import io.miragon.miranum.platform.user.adapter.mock.UserMockAdapter;
import io.miragon.miranum.platform.user.application.port.out.UserPort;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"io.miragon.miranum.platform.engine"})
@EnableJpaRepositories(basePackages = {"io.miragon.miranum.platform.engine"})
@ComponentScan(basePackages = {"io.miragon.miranum.platform.engine"})
@EnableFeignClients(clients = {SchemaClient.class})
public class MiranumEngineConfiguration {

    /**
     * Example implementation of the UserPort. Provide your own implementation.
     *
     * @return UserPort
     */
    @Bean
    @ConditionalOnMissingBean
    public UserPort userPort() {
        return new UserMockAdapter();
    }

}
