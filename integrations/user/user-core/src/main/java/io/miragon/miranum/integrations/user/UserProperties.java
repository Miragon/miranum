package io.miragon.miranum.integrations.user;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "miranum.user")
public class UserProperties {

    /**
     * Type of user.
     * Currently ldap or ldap.mock
     */
    public String user;
}

