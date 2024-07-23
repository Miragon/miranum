package io.miragon.miranum.platform.example.shared.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * User authentication provider.
 * Extracts the username from the token.
 */
@Component
@Profile("!no-security")
@RequiredArgsConstructor
@Slf4j
public class UserAuthenticationProvider {

    public static final String NAME_UNAUTHENTICATED_USER = "unauthenticated";

    @Value("${spring.security.oauth2.client.provider.keycloak.user-name-attribute}")
    private String userNameAttribute;

    @NonNull
    public String getLoggedInUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return (String) jwt.getClaims().get(userNameAttribute);
        }
        return NAME_UNAUTHENTICATED_USER;
    }

    @NonNull
    public List<String> getLoggedInUserRoles() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream().map(Object::toString).map(obj -> obj.replaceFirst("^ROLE_", "")).toList();
    }

}
