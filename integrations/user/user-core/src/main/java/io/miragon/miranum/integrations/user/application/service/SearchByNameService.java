package io.miragon.miranum.integrations.user.application.service;

import io.miragon.miranum.connect.worker.domain.Worker;
import io.miragon.miranum.integrations.user.application.port.in.SearchByNameParameter;
import io.miragon.miranum.integrations.user.application.port.in.SearchByNameQuery;
import io.miragon.miranum.integrations.user.application.port.out.LoadUserPort;
import io.miragon.miranum.integrations.user.domain.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchByNameService implements SearchByNameQuery {

    private final LoadUserPort loadUserPort;

    @Override
    @Worker(type = "searchByName")
    public Users searchByName(final SearchByNameParameter parameter) {
        var users = this.loadUserPort.findByName(parameter.getName());
        log.info("Fetched users: {}", users);
        return new Users(users.orElse(Arrays.asList()));
    }
}
