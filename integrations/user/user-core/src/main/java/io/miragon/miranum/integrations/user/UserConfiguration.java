package io.miragon.miranum.integrations.user;

import io.miragon.miranum.integrations.user.application.port.in.SearchByIdQuery;
import io.miragon.miranum.integrations.user.application.port.in.SearchByNameQuery;
import io.miragon.miranum.integrations.user.application.port.out.LoadUserPort;
import io.miragon.miranum.integrations.user.application.service.SearchByIdService;
import io.miragon.miranum.integrations.user.application.service.SearchByNameService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class UserConfiguration {

    private final LoadUserPort loadUserPort;

    @Bean
    public SearchByIdQuery searchByIdQuery() {
        return new SearchByIdService(this.loadUserPort);
    }

    @Bean
    public SearchByNameQuery searchByNameQuery() {
        return new SearchByNameService(this.loadUserPort);
    }
}
