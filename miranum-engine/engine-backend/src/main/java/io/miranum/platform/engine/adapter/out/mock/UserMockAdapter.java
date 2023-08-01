package io.miranum.platform.engine.adapter.out.mock;

import io.miranum.platform.engine.application.port.out.user.UserPort;
import io.miranum.platform.engine.domain.user.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserMockAdapter implements UserPort {

    final User user = new User("123456789", "johndoe", "John", "Doe", "john@doe.io");

    @Override
    public Optional<User> findById(String id) {
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.of(user);
    }

    @Override
    public List<User> findByNamesLike(String filter) {
        return List.of(user);
    }
}
