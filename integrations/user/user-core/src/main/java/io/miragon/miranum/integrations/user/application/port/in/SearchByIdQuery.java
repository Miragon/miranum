package io.miragon.miranum.integrations.user.application.port.in;

import io.miragon.miranum.integrations.user.domain.User;

public interface SearchByIdQuery {
    User searchById(final SearchByIdParameter parameter);
}
