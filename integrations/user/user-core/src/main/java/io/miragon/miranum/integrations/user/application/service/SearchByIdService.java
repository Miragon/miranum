package io.miragon.miranum.integrations.user.application.service;

import io.miragon.miranum.connect.worker.domain.Worker;
import io.miragon.miranum.integrations.user.application.port.in.SearchByIdParameter;
import io.miragon.miranum.integrations.user.application.port.in.SearchByIdQuery;
import io.miragon.miranum.integrations.user.application.port.out.LoadUserPort;
import io.miragon.miranum.integrations.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class SearchByIdService implements SearchByIdQuery {

    private final LoadUserPort loadUserPort;

    @Override
    @Worker(type = "searchById")
    public User searchById(final SearchByIdParameter parameter) {
        var user = this.loadUserPort.findById(parameter.getId());
        log.info("Fetched user: {}", user);
        return user.orElseThrow(() -> new IllegalArgumentException(String.format("User with the id %s does not exist.", parameter.getId())));
    }
}
