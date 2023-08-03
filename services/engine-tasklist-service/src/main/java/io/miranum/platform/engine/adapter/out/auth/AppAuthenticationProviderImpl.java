package io.miranum.platform.engine.adapter.out.auth;

import io.miranum.platform.engine.api.AppAuthenticationProvider;
import io.muenchendigital.digiwf.spring.security.authentication.UserAuthenticationProvider;
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
