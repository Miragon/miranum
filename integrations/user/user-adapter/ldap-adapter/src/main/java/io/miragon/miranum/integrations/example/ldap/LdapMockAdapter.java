package io.miragon.miranum.integrations.example.ldap;

import io.miragon.miranum.integrations.example.ldap.mapper.UserAttributesMapper;
import io.miragon.miranum.integrations.example.ldap.query.LdapQueryFactory;
import io.miragon.miranum.integrations.user.application.port.out.LoadUserPort;
import io.miragon.miranum.integrations.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.ldap.core.LdapTemplate;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LdapMockAdapter implements LoadUserPort {

    private final LdapTemplate ldapTemplate;

    private final LdapQueryFactory ldapQueryFactory;

    @Override
    public Optional<User> findById(String id) {
        var query = ldapQueryFactory.createPersonByIdQuery(id);
        var users = ldapTemplate.search(query, new UserAttributesMapper());
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public Optional<List<User>> findByName(String name) {
        var query = ldapQueryFactory.createPersonByNameQuery(name);
        var users = ldapTemplate.search(query, new UserAttributesMapper());
        return users.isEmpty() ? Optional.empty() : Optional.of(users);
    }
}