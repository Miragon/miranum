package io.miragon.miranum.integrations.user.adapter.in.api;

import io.miragon.miranum.integrations.user.adapter.in.api.mapper.UserTOMapper;
import io.miragon.miranum.integrations.user.adapter.in.api.mapper.UserTOMapperImpl;
import io.miragon.miranum.integrations.user.application.port.in.SearchForUserQuery;
import io.miragon.miranum.integrations.user.common.AppAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestApiConfiguration {

    @Bean
    public UserRestController userRestController(SearchForUserQuery searchForUserQuery, AppAuthenticationProvider appAuthenticationProvider, UserTOMapper mapper) {
        return new UserRestController(searchForUserQuery, appAuthenticationProvider, mapper);
    }

    @Bean
    public AppAuthenticationProvider appAuthenticationProvider() {
        return new AppAuthenticationProvider();
    }

    @Bean UserTOMapper userTOMapper() {
        return new UserTOMapperImpl();
    }
}
