package io.miragon.miranum.integrations.user.application.port.out;

import io.miragon.miranum.integrations.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface LoadUserPort {

    Optional<User> findById(final String id);

    Optional<List<User>> findByName(final String name);

}
