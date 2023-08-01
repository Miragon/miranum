package io.miranum.platform.engine.application.service;

import io.miranum.platform.engine.application.port.in.user.UserQuery;
import io.miranum.platform.engine.application.port.out.user.UserPort;
import io.miranum.platform.engine.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class UserQueryService implements UserQuery {

    private final UserPort userPort;

    @Override
    public List<User> searchUser(String query) {
        return userPort.findByNamesLike(query);
    }

    @Override
    public User getUserById(String id) {
        return userPort.findById(id).orElseThrow();
    }

    @Override
    public User getUserByUserName(String username) {
        return this.userPort.findByUsername(username).orElseThrow();
    }
}
