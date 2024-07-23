package io.miragon.miranum.platform.example.adapter.out.auth;

import io.miragon.miranum.platform.engine.api.AppAuthenticationProvider;
import io.miragon.miranum.platform.example.shared.configuration.UserAuthenticationProvider;
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
