package io.miragon.miranum.platform.example.shared.sso.webapp;

import io.miragon.miranum.platform.example.shared.sso.GrantedAuthoritiesExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.rest.security.auth.AuthenticationProvider;
import org.camunda.bpm.engine.rest.security.auth.AuthenticationResult;

import java.security.Principal;
import java.util.List;


/**
 * Camunda WEBAPP configuration.
 * Similar to camunda's {@link org.camunda.bpm.engine.rest.security.auth.impl.ContainerBasedAuthenticationProvider} but also adds SSO roles to the authentication result.
 */
public class OAuthContainerBasedAuthenticationProvider implements AuthenticationProvider {

    public static List<String> getTenants(Principal principal) {
        return List.of("default");
    }

    @Override
    public AuthenticationResult extractAuthenticatedUser(HttpServletRequest request, ProcessEngine engine) {
        Principal principal = request.getUserPrincipal();

        if (principal == null) {
            return AuthenticationResult.unsuccessful();
        }

        String name = principal.getName();
        if (name == null || name.isEmpty()) {
            return AuthenticationResult.unsuccessful();
        }

        AuthenticationResult result = AuthenticationResult.successful(name);
        result.setGroups(GrantedAuthoritiesExtractor.extractRoles(principal));

        final List<String> tenants = getTenants(principal);

        if (!tenants.isEmpty()) {
            result.setTenants(tenants);
        }

        return result;
    }

    @Override
    public void augmentResponseByAuthenticationChallenge(HttpServletResponse response, ProcessEngine engine) {
        // noop
    }
}
