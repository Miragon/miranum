package io.miragon.miranum.integrations.example.ldap.query;

import io.miragon.miranum.integrations.example.ldap.mapper.LdapAttributeConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.SearchScope;

import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

/**
 * Create predefines ldap queries.
 */
@RequiredArgsConstructor
public class LdapQueryFactory {

    private final LdapFilterFactory ldapFilterFactory;
    private final String personSearchBase;
    private final String ouSearchBase;

    public LdapQuery createPersonByIdQuery(final String id) {
        return query()
                .searchScope(SearchScope.SUBTREE)
                .base(personSearchBase)
                .where(LdapAttributeConstants.LDAP_OBJID).is(id);
    }

    public LdapQuery createPersonByUsernameQuery(final String username) {
        return query()
                .searchScope(SearchScope.SUBTREE)
                .base(personSearchBase)
                .where(LdapAttributeConstants.LDAP_UID).is(username);
    }

    public LdapQuery createPersonByNamePatternAndOuQuery(final String filter, final List<String> ouFilters) {
        return query()
                .searchScope(SearchScope.SUBTREE)
                .base(personSearchBase)
                .filter(new AndFilter()
                        .append(ldapFilterFactory.createOuNameFilter(ouFilters))
                        .append(ldapFilterFactory.createNamePatternFilter(filter))
                        .append(ldapFilterFactory.createPersonFilter()));
    }

    public LdapQuery createPersonByNamePatternsAndOuQuery(final String firstFilter, final String secondFilter, final List<String> ouFilters) {
        return query()
                .searchScope(SearchScope.SUBTREE)
                .base(personSearchBase)
                .filter(new AndFilter()
                        .append(ldapFilterFactory.createOuNameFilter(ouFilters))
                        .append(ldapFilterFactory.createNamePatternsFilter(firstFilter, secondFilter))
                );
    }

    public LdapQuery createOuByNameAndParentPathQuery(final String ouName, final String parentPath) {
        return query()
                .searchScope(SearchScope.SUBTREE)
                .base(parentPath)
                .filter(ldapFilterFactory.createOuNameFilter(ouName));
    }

    public LdapQuery createOuByShortNameQuery(final String shortName) {
        return query()
                .searchScope(SearchScope.SUBTREE)
                .base(ouSearchBase)
                .where(LdapAttributeConstants.LDAP_SHORTNAME).is(shortName);
    }

    public LdapQuery createPersonByObjectIdQuery(final String lhmObjectId) {
        return query()
                .searchScope(SearchScope.SUBTREE)
                .base(personSearchBase)
                .where(LdapAttributeConstants.LDAP_OBJID).is(lhmObjectId);
    }

}
