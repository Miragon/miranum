package io.miragon.miranum.platform.example.shared.sso;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;


public class TokenParsingOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private final TokenParsingOAuth2UserService delegate;

    public TokenParsingOidcUserService(TokenParsingOAuth2UserService delegate) {
        this.delegate = delegate;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        return new DefaultOidcUser(
                oAuth2User.getAuthorities(),
                userRequest.getIdToken(),
                new OidcUserInfo(oAuth2User.getAttributes()),
                userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName());
    }
}
