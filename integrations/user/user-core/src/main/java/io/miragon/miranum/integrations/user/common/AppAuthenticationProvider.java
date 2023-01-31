package io.miragon.miranum.integrations.user.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Get current camunda authentication information.
 *
 * @author externer.dl.horn
 */
@Component
@RequiredArgsConstructor
public class AppAuthenticationProvider {

    public List<String> getCurrentUserGroups() {
        return Arrays.asList();
    }

    public String getCurrentUserId() {
        return "current-user-id";
    }
}
