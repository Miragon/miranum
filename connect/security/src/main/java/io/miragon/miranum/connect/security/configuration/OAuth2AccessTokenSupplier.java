package io.miragon.miranum.connect.security.configuration;

import io.miragon.miranum.connect.security.properties.SpringSecurityProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@ConditionalOnProperty(value = "miranum.security.service-account", matchIfMissing = true)
@RequiredArgsConstructor
public class OAuth2AccessTokenSupplier implements Supplier<OAuth2AccessToken> {

    private final SpringSecurityProperties springSecurityProperties;

    private final OAuth2AuthorizedClientManager authorizedClientManager;
    private AnonymousAuthenticationToken anonymousUserToken;

    private static final String ACCESS_ROLE = "clientrole_user";
    public static final String SPRING_ROLE_PREFIX = "ROLE_";

    @PostConstruct
    void init() {
        anonymousUserToken = new AnonymousAuthenticationToken(
                springSecurityProperties.getClientRegistrationServiceAccount(),
                springSecurityProperties.getClientRegistrationServiceAccount(),
                AuthorityUtils.createAuthorityList(SPRING_ROLE_PREFIX + ACCESS_ROLE)
        );
    }

    @Override
    public OAuth2AccessToken get() {
        final OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId(
                        springSecurityProperties.getClientRegistrationServiceAccount()
                )
                .principal(anonymousUserToken)
                .build();
        final OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(authorizeRequest);
        if (authorizedClient == null) {
            throw new IllegalStateException("Client credentials authorization using client registration '" +
                    springSecurityProperties.getClientRegistrationServiceAccount() + "' failed.");
        }
        return authorizedClient.getAccessToken();
    }

}
