package io.miranum.platform.tasklist.application.port.out.user;

import org.springframework.lang.NonNull;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * Resolves groups of a user.
 */
public interface UserGroupResolverPort {

  /**
   * Resolves groups.
   *
   * @param userId identity of the user.
   * @return a set of groups in <strong>lower case</strong> or empty set, if no groups are found.
   */
  @Nonnull
  Set<String> resolveGroups(@NonNull String userId);
}
