package io.miragon.miranum.platform.example.engine.sso.webapp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class OAuthLogoutHandler implements LogoutSuccessHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private String oauth2UserLogoutUri;

    public OAuthLogoutHandler(@Value("${spring.security.oauth2.client.provider.keycloak.authorization-uri}") String oauth2UserAuthorizationUri) {
        if (!oauth2UserAuthorizationUri.isEmpty()) {
            // in order to get the valid logout uri: simply replace "/auth" at the end of the user authorization uri with "/logout"
            this.oauth2UserLogoutUri = oauth2UserAuthorizationUri.replace("openid-connect/auth", "openid-connect/logout");
        }
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (!oauth2UserLogoutUri.isEmpty()) {
            String requestUrl = request.getRequestURL().toString();
            String redirectUri = requestUrl.substring(0, requestUrl.indexOf("/api"));
            String idToken = ((OidcUser) authentication.getPrincipal()).getIdToken().getTokenValue();
            String logoutUrl = String.format("%s?post_logout_redirect_uri=%s&id_token_hint=%s",
                    oauth2UserLogoutUri,
                    URLEncoder.encode(redirectUri, StandardCharsets.UTF_8),
                    URLEncoder.encode(idToken, StandardCharsets.UTF_8));

            redirectStrategy.sendRedirect(request, response, logoutUrl);
        }
    }
}
