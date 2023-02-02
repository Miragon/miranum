package io.miragon.miranum.integrations.example.ldap.configuration;

import io.miragon.miranum.integrations.example.ldap.LdapAdapter;
import io.miragon.miranum.integrations.example.ldap.LdapMockAdapter;
import io.miragon.miranum.integrations.example.ldap.query.LdapQueryFactory;
import io.miragon.miranum.integrations.user.application.port.out.LoadUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

/**
 * This class provides the LdapTemplate as a Bean for Ldap querying purposes.<p>
 */
@Configuration
@EnableConfigurationProperties(ServiceAuthLdapProperties.class)
@RequiredArgsConstructor
public class LdapTemplateConfiguration {

    private final ServiceAuthLdapProperties serviceAuthLdapProperties;

    /**
     * The context for the LdapTemplate.
     * The context in which Ldap the search should be performed und what search base should be used.
     *
     * @return The LdapContextSource for a LdapTemplate
     */
    @Bean
    @ConditionalOnProperty(prefix = "miranum", value = "user", havingValue = "ldap")
    public LdapContextSource contextSourceTarget() {
        final LdapContextSource ldapContextSource = new LdapContextSource();
        ldapContextSource.setUrl(this.serviceAuthLdapProperties.getContextSource());
        return ldapContextSource;
    }

    @Bean
    public LdapQueryFactory ldapQueryFactory() {
        return new LdapQueryFactory(serviceAuthLdapProperties.getPersonSearchBase());
    }

    @Bean
    @ConditionalOnProperty(prefix = "miranum", value = "user", havingValue = "ldap")
    public LoadUserPort lhmLdapTemplate(final LdapQueryFactory ldapQueryFactory) {
        return new LdapAdapter(this.contextSourceTarget(), ldapQueryFactory);
    }

    @Bean
    @ConditionalOnProperty(prefix = "miranum", value = "user", havingValue = "ldap.mock", matchIfMissing = true)
    public LoadUserPort lhmLdapMockTemplate(final LdapTemplate ldapTemplate) {
        return new LdapMockAdapter(ldapTemplate, ldapQueryFactory());
    }
}
