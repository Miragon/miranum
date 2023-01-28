package io.miragon.miranum.integrations.user.application.service;

import io.miragon.miranum.integrations.user.application.port.in.SearchForUserQuery;
import io.miragon.miranum.integrations.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Fake implementation of the UserService to enable local development without an ldap.
 *
 * @author ext.dl.moesle
 */
@Profile("no-ldap")
@Slf4j
@Service
@RequiredArgsConstructor
public class MockUserServiceImpl implements SearchForUserQuery {

    private final List<String> groups = List.of("group1");
    private final User user = new User(
            "johnDoe",
            "0000000",
            "John",
            "Doe",
            "john.doe@example.com",
            "cn",
            "86153",
            "Boeheimstrasse 8",
            "group1",
            "Anon",
            "group1",
            "86153",
            "1",
            "Boeheimstrasse 8",
            "0000-0000000",
            "0000-0000000",
            true,
            Set.of("group1"),
            "",
            "group1"
    );

    @Override
    public List<String> getGroups(final String userId) {
        return this.groups;
    }

    @Override
    public List<User> searchUser(final String searchString, final String ous) {
        return List.of(this.user);
    }

    @Override
    public String getUserString(final String userId) {
        if (StringUtils.isBlank(userId)) {
            return null;
        }
        return this.user.getForename() +
                " " +
                this.user.getSurname() +
                " (" +
                this.user.getOu() +
                ")";
    }

    @Override
    public User getUser(final String userId) {
        return this.user;
    }

    @Override
    public Optional<User> getUserOrNull(final String userId) {
        return Optional.of(this.user);
    }

    @Override
    public Optional<User> getUserByUserName(final String username) {
        return Optional.of(this.user);
    }

    @Override
    public Optional<User> getOuByShortName(final String shortname) {
        return Optional.of(this.user);
    }
}
