package io.muenchendigital.digiwf.spring.security.authentication;

import io.muenchendigital.digiwf.spring.security.SpringSecurityProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.muenchendigital.digiwf.spring.security.SecurityConfiguration.SECURITY;

/**
 * User authentication provider.
 * Extracts the username from the token.
 */
@Component
@Profile(SECURITY)
@RequiredArgsConstructor
@Slf4j
public class UserAuthenticationProviderImpl implements UserAuthenticationProvider {

    public static final String NAME_UNAUTHENTICATED_USER = "unauthenticated";
    private final SpringSecurityProperties springSecurityProperties;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private String userNameAttribute;

    @PostConstruct
    public void getUsernameAttributeName() {
        try {
            userNameAttribute = clientRegistrationRepository.findByRegistrationId(springSecurityProperties.getClientRegistration())
                    .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        } catch (Exception e) {
            userNameAttribute = "user_name";
            log.error("Error reading username attribute for configured client registration "
                    + springSecurityProperties.getClientRegistration() + ". Falling back to " + userNameAttribute, e);
        }
    }

    @Override
    @NonNull
    public String getLoggedInUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return (String) jwt.getClaims().get(userNameAttribute);
        }
        return NAME_UNAUTHENTICATED_USER;
    }

    @Override
    @NonNull
    public List<String> getLoggedInUserRoles() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream().map(Object::toString).map(obj -> obj.replaceFirst("^ROLE_", "")).toList();
    }

}
