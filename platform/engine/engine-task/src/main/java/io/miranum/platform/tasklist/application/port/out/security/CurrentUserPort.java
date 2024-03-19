package io.miranum.platform.tasklist.application.port.out.security;


import io.miranum.platform.user.domain.User;

/**
 * Port to retrieve currently logged-in user information.
 */
public interface CurrentUserPort {
    /**
     * Retrieves current user.
     *
     * @return current user.
     */
    User getCurrentUser();

    /**
     * Retrieves the username (not the user id) of the current user.
     *
     * @return username of the current user.
     */
    String getCurrentUserUsername();
}
