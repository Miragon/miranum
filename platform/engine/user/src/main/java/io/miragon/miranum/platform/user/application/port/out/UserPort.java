package io.miragon.miranum.platform.user.application.port.out;


import io.miragon.miranum.platform.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserPort {

    Optional<User> findByUsername(String username);

    List<User> findByNamesLike(String filter);

    List<User> getUsersByGroup(String group);

}
