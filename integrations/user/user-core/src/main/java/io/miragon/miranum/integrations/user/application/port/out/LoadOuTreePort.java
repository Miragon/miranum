package io.miragon.miranum.integrations.user.application.port.out;

import java.util.List;

public interface LoadOuTreePort {

    /**
     * Get all groups of an user.
     *
     * @param id Id of the users
     * @return all groups
     */
    List<String> findGroupTree(String id);
}
