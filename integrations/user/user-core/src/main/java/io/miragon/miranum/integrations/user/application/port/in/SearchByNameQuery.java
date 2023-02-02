package io.miragon.miranum.integrations.user.application.port.in;

import io.miragon.miranum.integrations.user.domain.Users;

public interface SearchByNameQuery {
    Users searchByName(final SearchByNameParameter parameter);
}
