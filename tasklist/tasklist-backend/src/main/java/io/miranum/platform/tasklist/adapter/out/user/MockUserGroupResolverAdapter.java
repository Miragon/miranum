package io.miranum.platform.tasklist.adapter.out.user;

import io.miranum.platform.tasklist.application.port.out.user.UserGroupResolverPort;
import org.springframework.lang.NonNull;

import java.util.Set;


/**
 * Mock implementation of the group resolver returning the same group.
 */
public class MockUserGroupResolverAdapter implements UserGroupResolverPort {

  public static final String PRIMARY_USERGROUP = "group1";
  // per convention lower case
  private final Set<String> userGroups = Set.of("foo", "bar", PRIMARY_USERGROUP);

  @NonNull
  @Override
  public Set<String> resolveGroups(@NonNull final String userId) {
    return this.userGroups;
  }
}
