package io.muenchendigital.digiwf.spring.security.authentication;

import io.muenchendigital.digiwf.spring.security.SpringSecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static io.muenchendigital.digiwf.spring.security.NoSecurityConfiguration.NO_SECURITY;

/**
 * Security provider for no-security environments.
 */
@Component
@Profile(NO_SECURITY)
@RequiredArgsConstructor
public class NoSecurityUserAuthenticationProvider implements UserAuthenticationProvider {

    public final SpringSecurityProperties springSecurityProperties;

    @Override
    @NonNull
    public String getLoggedInUser() {
        return springSecurityProperties.getFallbackUsername();
    }

    @Override
    public List<String> getLoggedInUserRoles() {
        return Collections.emptyList();
    }

}
