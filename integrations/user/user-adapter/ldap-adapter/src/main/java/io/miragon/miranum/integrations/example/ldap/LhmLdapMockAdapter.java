package io.miragon.miranum.integrations.example.ldap;

import io.miragon.miranum.integrations.user.application.port.out.LoadGroupTreePort;
import io.miragon.miranum.integrations.user.application.port.out.LoadUserPort;
import io.miragon.miranum.integrations.user.domain.User;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class LhmLdapMockAdapter implements LoadGroupTreePort, LoadUserPort {

    private final List<String> groups = List.of("group1");
    private final User user = new User(
            "johnDoe",
            "0000000",
            "John",
            "Doe",
            "john.doe@example.com",
            "cn",
            "86153",
            "Boeheimstrasse 8",
            "group1",
            "Anon",
            "group1",
            "86153",
            "1",
            "Boeheimstrasse 8",
            "0000-0000000",
            "0000-0000000",
            true,
            Set.of("group1"),
            "",
            "group1"
    );

    @Override
    public List<String> findGroupTree(String id) {
        return this.groups;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.of(this.user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.of(this.user);
    }

    @Override
    public List<User> findByNamesLike(String filter, List<String> ouFilters) {
        return Arrays.asList(this.user);
    }

    @Override
    public List<User> findByNamesLike(String firstFilter, String secondFilter, List<String> ouFilters) {
        return Arrays.asList(this.user);
    }

    @Override
    public Optional<User> findGroupByShortName(String shortName) {
        return Optional.of(this.user);
    }
}
