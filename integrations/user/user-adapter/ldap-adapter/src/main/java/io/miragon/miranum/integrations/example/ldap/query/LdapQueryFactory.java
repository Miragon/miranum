package io.miragon.miranum.integrations.example.ldap.query;

import io.miragon.miranum.integrations.example.ldap.mapper.LdapAttributeConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.SearchScope;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

/**
 * Create predefines ldap queries.
 */
@RequiredArgsConstructor
public class LdapQueryFactory {

    private final String personSearchBase;

    public LdapQuery createPersonByIdQuery(final String id) {
        return query()
                .searchScope(SearchScope.SUBTREE)
                .base(personSearchBase)
                .where(LdapAttributeConstants.OBJECT_ID).is(id);
    }

    public LdapQuery createPersonByNameQuery(final String name) {
        return query()
                .searchScope(SearchScope.SUBTREE)
                .base(personSearchBase)
                .where(LdapAttributeConstants.FIRSTNAME).is(name)
                .or(LdapAttributeConstants.SURNAME).is(name);
    }
}
