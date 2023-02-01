package io.miragon.miranum.integrations.user.application.port.in;

import io.miragon.miranum.integrations.user.domain.User;

import java.util.List;

public interface SearchByNameQuery {
    List<User> searchByName(final SearchByNameParameter parameter);
}
