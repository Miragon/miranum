package io.miragon.miranum.integrations.user.adapter.in.api;

import io.miragon.miranum.integrations.user.adapter.in.api.mapper.UserTOMapper;
import io.miragon.miranum.integrations.user.adapter.in.api.mapper.UserTOMapperImpl;
import io.miragon.miranum.integrations.user.application.port.in.SearchForUserQuery;
import io.miragon.miranum.integrations.user.common.AppAuthenticationProvider;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.impl.IdentityServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestApiConfiguration {

    @Bean
    public UserRestController userRestController(SearchForUserQuery searchForUserQuery, AppAuthenticationProvider appAuthenticationProvider, UserTOMapper mapper) {
        return new UserRestController(searchForUserQuery, appAuthenticationProvider, mapper);
    }

    @Bean
    public IdentityService identityService() {
        return new IdentityServiceImpl();
    }

    @Bean
    public AppAuthenticationProvider appAuthenticationProvider() {
        return new AppAuthenticationProvider(this.identityService());
    }

    @Bean UserTOMapper userTOMapper() {
        return new UserTOMapperImpl();
    }
}
