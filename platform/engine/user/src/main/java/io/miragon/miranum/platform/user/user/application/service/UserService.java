package io.miragon.miranum.platform.user.user.application.service;

import io.miragon.miranum.platform.user.user.application.port.in.UserApi;
import io.miragon.miranum.platform.user.user.application.port.out.UserPort;
import io.miragon.miranum.platform.user.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
class UserService implements UserApi {

    private final UserPort userPort;

    @Override
    public List<User> searchUser(String query) {
        return userPort.findByNamesLike(query);
    }

    @Override
    public User getUserByUserName(String username) {
        return this.userPort.findByUsername(username).orElseThrow();
    }
}
