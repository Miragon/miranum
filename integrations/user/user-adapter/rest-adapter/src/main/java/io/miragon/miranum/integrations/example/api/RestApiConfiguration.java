package io.miragon.miranum.integrations.example.api;

import io.miragon.miranum.integrations.example.api.mapper.UserTOMapper;
import io.miragon.miranum.integrations.example.api.mapper.UserTOMapperImpl;
import io.miragon.miranum.integrations.user.application.port.in.SearchByIdQuery;
import io.miragon.miranum.integrations.user.application.port.in.SearchByNameQuery;
import io.miragon.miranum.integrations.user.common.AppAuthenticationProvider;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.impl.IdentityServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestApiConfiguration {

    @Bean
    public IdentityService identityService() {
        return new IdentityServiceImpl();
    }
    @Bean
    public UserRestController userRestController(SearchByIdQuery searchByIdQuery, SearchByNameQuery searchByNameQuery, AppAuthenticationProvider appAuthenticationProvider, UserTOMapper mapper) {
        return new UserRestController(searchByIdQuery, searchByNameQuery, appAuthenticationProvider, mapper);
    }

    @Bean
    public AppAuthenticationProvider appAuthenticationProvider(final IdentityService identityService) {
        return new AppAuthenticationProvider(identityService);
    }

    @Bean
    public UserTOMapper userTOMapper() {
        return new UserTOMapperImpl();
    }
}
