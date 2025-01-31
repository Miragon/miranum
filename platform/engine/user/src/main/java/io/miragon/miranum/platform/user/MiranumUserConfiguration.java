package io.miragon.miranum.platform.user;

import io.miragon.miranum.platform.user.adapter.mock.UserMockAdapter;
import io.miragon.miranum.platform.user.application.port.out.UserPort;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"io.miragon.miranum.platform.user"})
public class MiranumUserConfiguration {

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
