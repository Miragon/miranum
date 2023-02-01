package io.miragon.miranum.integrations.example.ldap;

import io.miragon.miranum.integrations.user.application.port.out.LoadUserPort;
import io.miragon.miranum.integrations.user.domain.User;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class LdapMockAdapter implements LoadUserPort {

    private final User user = new User(
            "JamesCHarris",
            "F345SDFdf",
            "James",
            "Harris",
            "JamesCHarris@rhyta.com",
            "Mr",
            "HR",
            Arrays.asList("group1", "group2")
    );

    @Override
    public Optional<User> findById(String id) {
        return Optional.of(this.user);
    }

    @Override
    public Optional<List<User>> findByName(String name) {
        return Optional.of(List.of(this.user));
    }
}
