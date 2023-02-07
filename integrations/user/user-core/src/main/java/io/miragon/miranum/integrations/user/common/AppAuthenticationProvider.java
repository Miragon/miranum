package io.miragon.miranum.integrations.user.common;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.IdentityService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Get current camunda authentication information.
 */
@Component
@RequiredArgsConstructor
public class AppAuthenticationProvider {

    private final IdentityService identityService;

    public List<String> getCurrentUserGroups() {
        return this.identityService.getCurrentAuthentication().getGroupIds();
    }

    public String getCurrentUserId() {
        return this.identityService.getCurrentAuthentication().getUserId();
    }
}
