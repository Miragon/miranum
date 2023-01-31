package io.miragon.miranum.integrations.user;

import io.miragon.miranum.integrations.user.application.port.in.SearchForUserQuery;
import io.miragon.miranum.integrations.user.application.port.out.LoadGroupTreePort;
import io.miragon.miranum.integrations.user.application.port.out.LoadUserPort;
import io.miragon.miranum.integrations.user.application.service.UserService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(UserProperties.class)
public class UserConfiguration {

    @Bean
    public SearchForUserQuery searchForUserQuery(final LoadGroupTreePort loadGroupTreePort, final LoadUserPort loadUserPort) {
        return new UserService(loadGroupTreePort, loadUserPort);
    }
}
