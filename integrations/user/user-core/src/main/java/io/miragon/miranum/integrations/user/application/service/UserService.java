package io.miragon.miranum.integrations.user.application.service;

import io.miragon.miranum.connect.worker.domain.Worker;
import io.miragon.miranum.integrations.user.application.port.in.SearchForUserParameter;
import io.miragon.miranum.integrations.user.application.port.in.SearchForUserQuery;
import io.miragon.miranum.integrations.user.application.port.out.LoadGroupTreePort;
import io.miragon.miranum.integrations.user.application.port.out.LoadUserPort;
import io.miragon.miranum.integrations.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Profile("!no-ldap")
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements SearchForUserQuery {

    private final LoadGroupTreePort loadGroupTreePort;
    private final LoadUserPort loadUserPort;

    @Override
    public List<String> getGroups(final String userId) {
        return this.loadGroupTreePort.findGroupTree(userId).stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    @Override
    public List<User> searchUser(final String searchString, final String groupId) {
        log.debug("Searching user with {}", searchString);

        if (StringUtils.isEmpty(searchString)) {
            throw new IllegalArgumentException("Expected value in request: queryString");
        }

        final List<String> oufilterList = groupId == null ? Collections.emptyList() : Arrays.asList(groupId.split(","));

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

    @Override
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

    @Override
    @Worker(type = "getUser")
    public User getUser(final SearchForUserParameter searchForUserParameter) {
        var user = this.loadUserPort.findById(searchForUserParameter.getUserId());
        log.info("Fetched user: {}", user);
        return user.orElseThrow(() -> new IllegalArgumentException(String.format("User with the id %s does not exist.", searchForUserParameter.getUserId())));
    }

    @Override
    public Optional<User> getUserOrNull(final String userId) {
        return this.loadUserPort.findById(userId);
    }

    @Override
    public Optional<User> getUserByUserName(final String username) {
        return this.loadUserPort.findByUsername(username);
    }

    @Override
    public Optional<User> getOuByShortName(final String shortname) {
        return this.loadUserPort.findGroupByShortName(shortname);
    }

}
