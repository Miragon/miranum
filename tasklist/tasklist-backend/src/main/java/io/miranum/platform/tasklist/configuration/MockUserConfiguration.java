package io.miranum.platform.tasklist.configuration;

import io.miranum.platform.tasklist.adapter.out.user.MockUserGroupResolverAdapter;
import io.miranum.platform.tasklist.adapter.out.user.MockUserProfileAdapter;
import io.miranum.platform.tasklist.application.port.out.user.UserGroupResolverPort;
import io.miranum.platform.tasklist.application.port.out.user.UserProfilePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configures authentication and authorization facilities.
 */
@Profile("mock-user")
@Configuration
public class MockUserConfiguration {


    /**
     * Mock resolver not using LDAP but always returning groups "group1" and "group2".
     *
     * @return mock user group resolver.
     */
    @Bean
    public UserGroupResolverPort mockUserGroupResolver() {
        return new MockUserGroupResolverAdapter();
    }

    /**
     * Mock adapter not using LDAP always returning the "john.doe" user.
     *
     * @return mock user profile adapter.
     */
    @Bean
    public UserProfilePort mockUserProfileAdapter() {
        return new MockUserProfileAdapter();
    }
}
