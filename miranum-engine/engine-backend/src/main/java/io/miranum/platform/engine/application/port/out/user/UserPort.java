package io.miranum.platform.engine.application.port.out.user;


import io.miranum.platform.engine.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface UserPort {

    Optional<User> findByUsername(String username);

    List<User> findByNamesLike(String filter);

}
