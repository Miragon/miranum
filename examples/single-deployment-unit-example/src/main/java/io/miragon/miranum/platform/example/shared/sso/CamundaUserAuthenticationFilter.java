package io.miragon.miranum.platform.example.shared.sso;

import io.miragon.miranum.platform.example.shared.sso.rest.ServiceAccountAuthenticationProvider;
import jakarta.servlet.*;
import org.camunda.bpm.engine.IdentityService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Camunda ENGINE configuration.
 * Filter to set authentication / authorization information.
 * This information is used for restrict access to resources.
 */
class CamundaUserAuthenticationFilter implements Filter {

    private final IdentityService identityService;
    private final ServiceAccountAuthenticationProvider userAuthenticationProvider;

    public CamundaUserAuthenticationFilter(
            final IdentityService identityService,
            final ServiceAccountAuthenticationProvider userAuthenticationProvider
    ) {
        this.identityService = identityService;
        this.userAuthenticationProvider = userAuthenticationProvider;
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final String userId = userAuthenticationProvider.getLoggedInUser();
        final List<String> roles = new ArrayList<>(userAuthenticationProvider.getLoggedInUserRoles());
        try {
            identityService.setAuthentication(userId, roles, userAuthenticationProvider.getTenant());
            chain.doFilter(request, response);
        } finally {
            identityService.clearAuthentication();
        }
    }
}
