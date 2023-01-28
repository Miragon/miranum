package io.miragon.miranum.integrations.user.application.port.out;

import io.miragon.miranum.integrations.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface LoadUserPort {

    /**
     * Get user by id.
     *
     * @param id Id of the user
     * @return user optional
     */
    Optional<User> findById(String id);

    /**
     * Get user by username.
     *
     * @param username Username of the user.
     * @return user optional
     */
    Optional<User> findByUsername(String username);

    /**
     * Get users by name and groups.
     *
     * @param filter    Filter string for the names
     * @param ouFilters Groups to filter for
     * @return users
     */
    List<User> findByNamesLike(String filter, List<String> ouFilters);

    /**
     * Get users by name and groups.
     * Second and first filter do have an "or" logic.
     *
     * @param firstFilter  Filter string for the names
     * @param secondFilter Filter string for the names
     * @param ouFilters    Groups to filter for
     * @return users
     */
    List<User> findByNamesLike(String firstFilter, String secondFilter, List<String> ouFilters);

    /**
     * Get ou by shortname.
     */
    Optional<User> findOuByShortName(String shortName);
}
