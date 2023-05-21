package io.miranum.platform.tasklist.application.port.out.user;

import io.miranum.platform.tasklist.domain.UserProfile;
import org.springframework.lang.NonNull;

/**
 * Port to resolve username and return a user profile.
 */
public interface UserProfilePort {
    /**
     * Find a user by user id
     *
     * @param userId user id.
     * @return User profile
     * @throws UserNotFoundException if no user could not be found.
     */
    @NonNull
    UserProfile findUser(@NonNull String userId) throws UserNotFoundException;
}
