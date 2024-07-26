

package io.miragon.miranum.platform.example.engine.sso.rest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.*;

import static io.miragon.miranum.platform.example.engine.sso.GrantedAuthoritiesExtractor.SPRING_ROLE_PREFIX;

/**
 * User authentication provider.
 * Extracts the username from the token.
 */
@Component
public class ServiceAccountAuthenticationProvider {

    public static final String NAME_UNAUTHENTICATED = "unauthenticated";
    private final static String SERVICE_ACCOUNT_CLIENT_NAME_CLAIM = "clientId";

    public String getLoggedInUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AbstractAuthenticationToken && authentication.getPrincipal() instanceof Jwt jwt) {
            String clientId = (String) jwt.getClaims().get(SERVICE_ACCOUNT_CLIENT_NAME_CLAIM);
            return Objects.isNull(clientId) ? NAME_UNAUTHENTICATED : clientId;
        }
        return NAME_UNAUTHENTICATED;
    }

    public List<String> getTenant() {
        return List.of("default");
    }

    public Set<String> getLoggedInUserRoles() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final List<String> extractedRoles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(role -> StringUtils.removeStart(role, SPRING_ROLE_PREFIX))
                .toList();
        return new HashSet<>(extractedRoles);
    }
}
