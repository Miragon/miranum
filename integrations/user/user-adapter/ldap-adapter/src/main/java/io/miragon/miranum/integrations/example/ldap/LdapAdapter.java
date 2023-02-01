package io.miragon.miranum.integrations.example.ldap;

import io.miragon.miranum.integrations.example.ldap.mapper.UserAttributesMapper;
import io.miragon.miranum.integrations.example.ldap.query.LdapQueryFactory;
import io.miragon.miranum.integrations.user.application.port.out.LoadUserPort;
import io.miragon.miranum.integrations.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;

import java.util.List;
import java.util.Optional;

@Slf4j
public class LdapAdapter extends LdapTemplate implements LoadUserPort {

    private final LdapQueryFactory ldapQueryFactory;

    public LdapAdapter(final ContextSource contextSource, final LdapQueryFactory ldapQueryFactory) {
        super(contextSource);
        this.ldapQueryFactory = ldapQueryFactory;
    }

    @Override
    public Optional<User> findById(final String id) {
        log.debug("Get LDAP information for user with id: {}.", id);
        final LdapQuery query = ldapQueryFactory.createPersonByIdQuery(id);

        val userList = super.search(query, new UserAttributesMapper());
        if (userList.size() == 1) {
            return Optional.ofNullable(userList.get(0));
        }

        log.warn("User with id {} not identifiable!", id);
        return Optional.empty();
    }

    @Override
    public Optional<List<User>> findByName(String name) {
        log.debug("Get LDAP information for users with name: {}.", name);
        final LdapQuery query = ldapQueryFactory.createPersonByNameQuery(name);
        var userList = super.search(query, new UserAttributesMapper());
        return Optional.ofNullable(userList);
    }
}
