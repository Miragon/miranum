package io.miragon.miranum.platform.example.engine.sso;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.ClaimAccessor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.*;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Component
public class GrantedAuthoritiesExtractor implements Converter<Jwt, AbstractAuthenticationToken> {

    public static final String SPRING_ROLE_PREFIX = "ROLE_";
    private static final String ROLE_DECLARATIONS = "roles";
    private static final String REALM_ROLES_CLAIM = "realm_access";
    private static final String CLIENTS_CLAIM = "resource_access";
    private static final String CLIENT_ROLE_SEPARATOR = ":";

    @Value("${spring.security.oauth2.client.provider.keycloak.user-name-attribute}")
    private String principalClaimName;

    public static List<String> getClientAuthorities(ClaimAccessor jwt) {
        // retrieve client roles of all clients
        final List<String> clientAuthorities = new ArrayList<>();
        Map<String, Object> clientClaims = jwt.getClaimAsMap(CLIENTS_CLAIM);
        if (clientClaims != null) {
            clientClaims.forEach((client, claims) -> clientAuthorities.addAll(extractRoles(client, (Map<String, Object>) claims)));
        }
        return clientAuthorities;
    }

    static List<String> extractRoles(String client, Map<String, Object> clientObject) {
        final Collection<String> clientRoles = (Collection<String>) clientObject.get(ROLE_DECLARATIONS);
        if (clientRoles != null) {
            return clientRoles
                    .stream()
                    .map(role -> client + CLIENT_ROLE_SEPARATOR + role)
                    .collect(toList());
        } else {
            return Collections.emptyList();
        }
    }

    public static List<String> extractRoles(Principal principal) {
        if (principal instanceof Authentication) {
            return ((Authentication) principal).getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .map(role -> StringUtils.removeStart(role, SPRING_ROLE_PREFIX))
                    .collect(toList());
        }
        return emptyList();
    }

    @Override
    public final AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = this.extractAuthorities(jwt);

        String principalClaimValue = jwt.getClaimAsString(this.principalClaimName);
        return new JwtAuthenticationToken(jwt, authorities, principalClaimValue);
    }

    public void setPrincipalClaimName(String principalClaimName) {
        Assert.hasText(principalClaimName, "principalClaimName cannot be empty");
        this.principalClaimName = principalClaimName;
    }

    protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {

        // Retrieve client roles of all clients
        final Collection<String> clientAuthorities = getClientAuthorities(jwt);

        // Retrieve realm roles
        final Map<String, Object> realmAccess = jwt.getClaimAsMap(REALM_ROLES_CLAIM);

        Collection<String> realmAuthorities = Collections.emptyList();
        if (realmAccess != null && realmAccess.containsKey(ROLE_DECLARATIONS)) {
            realmAuthorities = (Collection<String>) realmAccess.get(ROLE_DECLARATIONS);
        }

        return Stream.concat(realmAuthorities.stream(), clientAuthorities.stream())
                .map(s -> SPRING_ROLE_PREFIX + s)
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
    }
}
