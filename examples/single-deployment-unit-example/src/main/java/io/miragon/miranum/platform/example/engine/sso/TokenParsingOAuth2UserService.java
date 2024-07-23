package io.miragon.miranum.platform.example.engine.sso;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenParsingOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final GrantedAuthoritiesExtractor grantedAuthoritiesExtractor;
    private final ConcurrentHashMap<String, JwtDecoder> jwtDecoders = new ConcurrentHashMap<>();

    public TokenParsingOAuth2UserService(GrantedAuthoritiesExtractor grantedAuthoritiesExtractor) {
        this.grantedAuthoritiesExtractor = grantedAuthoritiesExtractor;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        JwtDecoder jwtDecoder = jwtDecoders.computeIfAbsent(
                clientRegistration.getRegistrationId(),
                ignored -> NimbusJwtDecoder.withJwkSetUri(clientRegistration.getProviderDetails().getJwkSetUri()).build()
        );
        Jwt jwt = jwtDecoder.decode(userRequest.getAccessToken().getTokenValue());
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) grantedAuthoritiesExtractor.convert(jwt);

        return new DefaultOAuth2User(
                authenticationToken.getAuthorities(),
                authenticationToken.getTokenAttributes(),
                clientRegistration.getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName()
        );
    }
}
