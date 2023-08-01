package io.miranum.platform.tasklist.domain;

import lombok.Data;
import org.springframework.lang.NonNull;

/**
 * Represents the profile of the user.
 */
@Data
public class UserProfile {
  private final String userId;
  private final String firstName;
  private final String lastName;
  private final String primaryOrgUnit;

  /**
   * Factory method to create unknown users.
   * @param userId user id of the unknown user.
   * @return Null object.
   */
  public static UserProfile createUnknownUser(@NonNull String userId) {
    return new UserProfile(
        userId,
        "Unbekannt",
        "Unbekannt",
        "keine"
    );
  }
}
