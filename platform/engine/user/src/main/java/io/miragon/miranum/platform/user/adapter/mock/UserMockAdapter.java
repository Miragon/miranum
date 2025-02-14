package io.miragon.miranum.platform.user.adapter.mock;

import io.miragon.miranum.platform.user.application.port.out.UserPort;
import io.miragon.miranum.platform.user.domain.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class UserMockAdapter implements UserPort {

    final User alex = new User("alex.admin@example.com", "Alex", "Admin", "alex.admin@example.com");
    final User oliver = new User("oliver.office@example.com", "Oliver", "Office", "oliver.office@example.com");
    final User olga = new User("olga.office@example.com", "Olga", "Office", "olga.office@example.com");
    final Map<String, User> users = Map.of("alex.admin@example.com", alex, "oliver.office@example.com", oliver, "olga.office@example.com", olga);

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }

    @Override
    public List<User> findByNamesLike(String filter) {
        return Stream.of(alex, oliver, olga)
                .filter(user -> user.getForename().contains(filter) || user.getSurname().contains(filter))
                .toList();
    }

    @Override
    public List<User> getUsersByGroup(String group) {
        // dummy implementation - just return all users
        return users.values().stream()
                .toList();
    }
}
