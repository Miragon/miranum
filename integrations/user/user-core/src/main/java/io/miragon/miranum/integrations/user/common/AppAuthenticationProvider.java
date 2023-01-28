package io.miragon.miranum.integrations.user.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Get current camunda authentication informaiton.
 *
 * @author externer.dl.horn
 */
@Component
@RequiredArgsConstructor
public class AppAuthenticationProvider {


    public List<String> getCurrentUserGroups() {
        // return this.identityService.getCurrentAuthentication().getGroupIds();
        return Arrays.asList();
    }

    public String getCurrentUserId() {
        // return this.identityService.getCurrentAuthentication().getUserId();
        return "current_user";
    }
}
