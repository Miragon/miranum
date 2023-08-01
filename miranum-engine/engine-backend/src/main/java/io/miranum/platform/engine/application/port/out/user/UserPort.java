package io.miranum.platform.engine.application.port.out.user;


import io.miranum.platform.engine.domain.user.User;

import java.util.List;
import java.util.Optional;

/**
 * Repository to load users.
 *
 * @author externer.dl.horn
 */
public interface UserPort {

    Optional<User> findById(String id);

    Optional<User> findByUsername(String username);

    List<User> findByNamesLike(String filter);

}
