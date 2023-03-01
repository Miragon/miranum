package io.miragon.miranum.integrations.example.api;

import io.miragon.miranum.integrations.example.api.mapper.UserTOMapper;
import io.miragon.miranum.integrations.example.api.mapper.UserTOMapperImpl;
import io.miragon.miranum.integrations.user.application.port.in.SearchByIdQuery;
import io.miragon.miranum.integrations.user.application.port.in.SearchByNameQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestApiConfiguration {

    @Bean
    public UserRestController userRestController(final SearchByIdQuery searchByIdQuery, final SearchByNameQuery searchByNameQuery, final UserTOMapper mapper) {
        return new UserRestController(searchByIdQuery, searchByNameQuery, mapper);
    }


    @Bean
    public UserTOMapper userTOMapper() {
        return new UserTOMapperImpl();
    }
}
