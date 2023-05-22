package io.miranum.platform.engine.api;

import java.util.List;

public interface AppAuthenticationProvider {
    String getCurrentUserId();

    List<String> getCurrentUserGroups();
}
