package io.miragon.miranum.integrations.example.ldap.query;

import io.miragon.miranum.integrations.example.ldap.configuration.ServiceAuthLdapProperties;
import io.miragon.miranum.integrations.example.ldap.mapper.LdapAttributeConstants;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.ldap.filter.*;

import java.util.Arrays;
import java.util.List;

/**
 * Create predefined filters for ldap queries.
 */
@RequiredArgsConstructor
public class LdapFilterFactory {

    private final ServiceAuthLdapProperties serviceAuthLdapProperties;

    public Filter createOuNameFilter(final String ouName) {
        return new AndFilter()
                .and(new EqualsFilter(LdapAttributeConstants.LDAP_OU, ouName))
                .and(createOuFilter());
    }

    public Filter createNamePatternFilter(final String pattern) {
        return new OrFilter()
                .append(new LikeFilter(LdapAttributeConstants.LDAP_GIVENNAME, this.createStartsWithExpression(pattern)))
                .append(new LikeFilter(LdapAttributeConstants.LDAP_SURNAME, this.createStartsWithExpression(pattern)));
    }

    public Filter createNamePatternsFilter(final String firstPattern, final String secondPattern) {
        val firstAndFilter = new AndFilter()
                .append(new LikeFilter(LdapAttributeConstants.LDAP_GIVENNAME, this.createStartsWithExpression(firstPattern)))
                .append(new LikeFilter(LdapAttributeConstants.LDAP_SURNAME, this.createStartsWithExpression(secondPattern)));

        val secondAndFilter = new AndFilter()
                .append(new LikeFilter(LdapAttributeConstants.LDAP_GIVENNAME, this.createStartsWithExpression(secondPattern)))
                .append(new LikeFilter(LdapAttributeConstants.LDAP_SURNAME, this.createStartsWithExpression(firstPattern)));

        return new OrFilter()
                .append(firstAndFilter)
                .append(secondAndFilter);
    }

    private String createStartsWithExpression(final String pattern) {
        if (pattern.endsWith("*")) {
            return pattern;
        }
        return pattern + "*";
    }

    public Filter createOuNameFilter(final List<String> ouNames) {
        val ouFilter = new OrFilter();
        ouNames.forEach(ou -> ouFilter.append(new EqualsFilter(LdapAttributeConstants.LDAP_OU, ou)));
        return ouFilter;
    }

    public Filter createPersonFilter() {
        return createObjectClassFilter(Arrays.asList(this.serviceAuthLdapProperties.getPersonObjectClasses().split(",")));
    }

    public Filter createOuFilter() {
        return createObjectClassFilter(Arrays.asList(this.serviceAuthLdapProperties.getOuObjectClasses().split(",")));
    }

    private Filter createObjectClassFilter(final List<String> objectClasses) {
        final AndFilter filter = new AndFilter();
        objectClasses.forEach(objectClass -> filter.append(new EqualsFilter(LdapAttributeConstants.LDAP_OBJECT_CLASS, objectClass)));
        return filter;
    }

}
