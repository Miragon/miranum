package io.miragon.miranum.integrations.user.application.service;

import io.miragon.miranum.connect.worker.domain.Worker;
import io.miragon.miranum.integrations.user.application.port.in.SearchForUserQuery;
import io.miragon.miranum.integrations.user.application.port.out.LoadOuTreePort;
import io.miragon.miranum.integrations.user.application.port.out.LoadUserPort;
import io.miragon.miranum.integrations.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service to query for users.
 *
 * @author externer.dl.horn
 */
@Profile("!no-ldap")
@Slf4j
@Service
@RequiredArgsConstructor
public class SearchForUserService implements SearchForUserQuery {

    private final LoadOuTreePort loadOuTreePort;
    private final LoadUserPort loadUserPort;

    /**
     * Get groups for the given user id.
     *
     * @param userId Id for the user
     * @return groups in lowercase
     */
    @Override
    public List<String> getGroups(final String userId) {
        return this.loadOuTreePort.findOuTree(userId).stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    /**
     * Search users by the given string and groups.
     *
     * @param searchString Search string. is split by a space
     * @param ous          Groups of the user
     * @return users
     */
    @Override
    public List<User> searchUser(final String searchString, final String ous) {
        log.debug("Searching user with {}", searchString);

        if (StringUtils.isEmpty(searchString)) {
            throw new IllegalArgumentException("Expected value in request: queryString");
        }

        final List<String> oufilterList = ous == null ? Collections.emptyList() : Arrays.asList(ous.split(","));

        final String[] queryStrings = searchString.strip().split(" ");
        final List<User> userInfos = new ArrayList<>();
        if (queryStrings.length >= 2 && !StringUtils.isEmpty(queryStrings[0]) && !StringUtils.isEmpty(queryStrings[1])) {
            final List<User> userInfosByName = this.loadUserPort.findByNamesLike(queryStrings[0].strip(), queryStrings[1].strip(), oufilterList);
            userInfos.addAll(userInfosByName);
        } else {
            final List<User> userInfosByName = this.loadUserPort.findByNamesLike(searchString.strip(), oufilterList);
            userInfos.addAll(userInfosByName);
        }

        log.debug("Hits for {}: {}", searchString, userInfos.size());
        return userInfos;
    }

    /**
     * Format a specific user.
     *
     * @param userId Id of the user
     * @return the formatted user string
     */
    @Override
    @Worker(type = "getUserString")
    public String getUserString(final String userId) {
        if (StringUtils.isBlank(userId)) {
            return null;
        }
        final Optional<User> user = this.getUserOrNull(userId);
        return user.map(value -> value.getForename() +
                " " +
                value.getSurname() +
                " (" +
                value.getOu() +
                ")").orElse(userId);
    }

    /**
     * Get user by id.
     *
     * @param userId Id of the user
     * @return user
     */
    @Override
    public User getUser(final String userId) {
        return this.loadUserPort.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with the id %s does not exist.", userId)));
    }

    /**
     * Get user by id.
     *
     * @param userId Id of the user
     * @return user optional
     */
    @Override
    public Optional<User> getUserOrNull(final String userId) {
        return this.loadUserPort.findById(userId);
    }

    /**
     * Get user by username.
     *
     * @param username Username of the user
     * @return user optional
     */
    @Override
    public Optional<User> getUserByUserName(final String username) {
        return this.loadUserPort.findByUsername(username);
    }

    /**
     * Get OU by shortname.
     */
    @Override
    public Optional<User> getOuByShortName(final String shortname) {
        return this.loadUserPort.findOuByShortName(shortname);
    }

}
