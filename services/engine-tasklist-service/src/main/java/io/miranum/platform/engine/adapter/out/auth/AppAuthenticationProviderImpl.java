package io.miranum.platform.engine.adapter.out.auth;

import io.miranum.platform.engine.api.AppAuthenticationProvider;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class AppAuthenticationProviderImpl implements AppAuthenticationProvider {

    @Override
    public String getCurrentUserId() {
        return "123456789";
    }

    @Override
    public List<String> getCurrentUserGroups() {
        return Collections.emptyList();
    }
}
