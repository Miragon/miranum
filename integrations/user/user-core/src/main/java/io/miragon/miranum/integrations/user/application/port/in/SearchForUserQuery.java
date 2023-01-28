package io.miragon.miranum.integrations.user.application.port.in;

import io.miragon.miranum.integrations.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface SearchForUserQuery {

    /**
     * Get groups for the given user id.
     *
     * @param userId Id for the user
     * @return groups in lowercase
     */
    List<String> getGroups(final String userId);

    /**
     * Search users by the given string and groups.
     *
     * @param searchString Search string. is split by a space
     * @param ous          Groups of the user
     * @return users
     */
    List<User> searchUser(final String searchString, final String ous);

    /**
     * Format a specific user.
     *
     * @param userId Id of the user
     * @return the formatted user string
     */
    String getUserString(final String userId);

    /**
     * Get user by id.
     *
     * @param userId Id of the user
     * @return user
     */
    User getUser(final String userId);

    /**
     * Get user by id.
     *
     * @param userId Id of the user
     * @return user optional
     */
    Optional<User> getUserOrNull(final String userId);

    /**
     * Get user by username.
     *
     * @param username Username of the user
     * @return user optional
     */
    Optional<User> getUserByUserName(final String username);

    /**
     * Get OU by shortname.
     */
    Optional<User> getOuByShortName(final String shortname);
}
