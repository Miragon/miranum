package io.miranum.platform.engine.application.port.in.user;

import io.miranum.platform.engine.domain.user.User;

import java.util.List;

public interface UserQuery {

    List<User> searchUser(String query);

    User getUserById(String id);

    User getUserByUserName(String username);
}
