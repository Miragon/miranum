package io.miranum.platform.tasklist.adapter.out.user;

import io.miranum.platform.tasklist.application.port.out.user.UserGroupResolverPort;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.Set;


/**
 * Mock implementation of the group resolver returning the same group.
 */
public class MockUserGroupResolverAdapter implements UserGroupResolverPort {

    public static final String GROUP1 = "group1";

    @NonNull
    @Override
    public Set<String> resolveGroups(@NonNull String userId) {
        return Collections.singleton(GROUP1);
    }
}
