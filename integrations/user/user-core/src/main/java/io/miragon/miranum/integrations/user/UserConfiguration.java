package io.miragon.miranum.integrations.user;

import io.miragon.miranum.integrations.user.adapter.out.ldap.configuration.LdapTemplateConfiguration;
import io.miragon.miranum.integrations.user.application.port.in.SearchForUserQuery;
import io.miragon.miranum.integrations.user.application.port.out.LoadOuTreePort;
import io.miragon.miranum.integrations.user.application.port.out.LoadUserPort;
import io.miragon.miranum.integrations.user.application.service.SearchForUserService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(UserProperties.class)
@Import(LdapTemplateConfiguration.class)
public class UserConfiguration {

    @Bean
    public SearchForUserQuery sendMailUseCase(final LoadOuTreePort loadOuTreePort, final LoadUserPort loadUserPort) {
        return new SearchForUserService(loadOuTreePort, loadUserPort);
    }
}
