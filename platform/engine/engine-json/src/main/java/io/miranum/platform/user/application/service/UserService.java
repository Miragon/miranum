package io.miranum.platform.user.application.service;

import io.miranum.platform.user.application.port.in.UserApi;
import io.miranum.platform.user.application.port.out.UserPort;
import io.miranum.platform.user.domain.User;
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
