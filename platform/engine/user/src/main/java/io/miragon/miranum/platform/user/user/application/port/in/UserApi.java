package io.miragon.miranum.platform.user.user.application.port.in;


import io.miragon.miranum.platform.user.user.domain.User;

import java.util.List;

public interface UserApi {

    List<User> searchUser(String query);

    User getUserByUserName(String username);
}
