package io.miranum.platform.engine.adapter.out.auth;

import io.holunda.polyflow.view.auth.User;
import io.miranum.platform.tasklist.application.port.out.security.CurrentUserPort;
import io.muenchendigital.digiwf.spring.security.authentication.UserAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * Service to resolve currently logged-in user.
 */
@Component
@RequiredArgsConstructor
public class CurrentUserSpringSecurityAdapter implements CurrentUserPort {
    private final UserAuthenticationProvider userAuthenticationProvider;


    @Override
    public User getCurrentUser() {


        return new User(userAuthenticationProvider.getLoggedInUser(), new HashSet<>(userAuthenticationProvider.getLoggedInUserRoles()));
    }

    /**
     * Retrieves the username of the current user.
     *
     * @return username of the current user (from the user_name claim).
     */
    @Override
    public String getCurrentUserUsername() {
        return getCurrentUser().getUsername();
    }

}
