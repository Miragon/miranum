package io.miranum.platform.tasklist.configuration;

import io.miranum.platform.tasklist.adapter.out.user.LdapUserGroupResolverAdapter;
import io.miranum.platform.tasklist.adapter.out.user.LdapUserProfileAdapter;
import io.miranum.platform.tasklist.adapter.out.user.easyldap.EasyLdapClient;
import io.miranum.platform.tasklist.application.port.out.user.UserGroupResolverPort;
import io.miranum.platform.tasklist.application.port.out.user.UserProfilePort;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configures authentication and authorization facilities.
 */
@Profile("ldap-user")
@Configuration
@EnableFeignClients(clients = {EasyLdapClient.class})
public class LdapUserConfiguration {

    @Bean
    public UserGroupResolverPort easyLdapUserGroupResolver(EasyLdapClient easyLdapClient) {
        return new LdapUserGroupResolverAdapter(easyLdapClient);
    }

    @Bean
    public UserProfilePort easyLdapUserProfileAdapter(EasyLdapClient easyLdapClient) {
        return new LdapUserProfileAdapter(easyLdapClient);
    }
}
