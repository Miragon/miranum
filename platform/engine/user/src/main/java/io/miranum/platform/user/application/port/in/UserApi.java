package io.miranum.platform.user.application.port.in;


import io.miranum.platform.user.domain.User;

import java.util.List;

public interface UserApi {

    List<User> searchUser(String query);

    User getUserByUserName(String username);
}
