package io.miragon.miranum.platform.example.example.adapter.out.auth;

import io.miragon.miranum.platform.engine.api.AppAuthenticationProvider;
import io.miragon.miranum.platform.security.security.authentication.UserAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AppAuthenticationProviderImpl implements AppAuthenticationProvider {

    private final UserAuthenticationProvider userAuthenticationProvider;

    @Override
    public String getCurrentUserId() {
        return userAuthenticationProvider.getLoggedInUser();
    }

    @Override
    public List<String> getCurrentUserGroups() {
        return userAuthenticationProvider.getLoggedInUserRoles();
    }
}
