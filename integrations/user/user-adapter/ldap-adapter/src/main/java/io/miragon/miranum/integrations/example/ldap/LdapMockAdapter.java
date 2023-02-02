package io.miragon.miranum.integrations.example.ldap;

import io.miragon.miranum.integrations.example.ldap.mapper.UserAttributesMapper;
import io.miragon.miranum.integrations.example.ldap.query.LdapQueryFactory;
import io.miragon.miranum.integrations.user.application.port.out.LoadUserPort;
import io.miragon.miranum.integrations.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.ldap.core.LdapTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class LdapMockAdapter implements LoadUserPort {

    private final LdapTemplate ldapTemplate;

    private final LdapQueryFactory ldapQueryFactory;

    private final User jamesCHarris = new User(
            "JamesCHarris",
            "id1",
            "James",
            "Harris",
            "JamesCHarris@rhyta.com",
            "Mr",
            "HR",
            Arrays.asList("group1", "group2")
    );

    private final User lonnieBRitzmann = new User(
            "LonnieBRitzmann",
            "id2",
            "Lonnie",
            "Ritzmann",
            "LonnieBRitzmann@armyspy.com",
            "Mrs",
            "DEV",
            Arrays.asList("group1")
    );

    private final User jamesRossback = new User(
            "JamesRossback",
            "id3",
            "James",
            "Rossback",
            "JamesRossback@miga.com",
            "Mr",
            "ACC",
            Arrays.asList("group2")
    );

    //private final List<User> users = Arrays.asList(jamesCHarris, lonnieBRitzmann, jamesRossback);

    @Override
    public Optional<User> findById(String id) {
        var query = ldapQueryFactory.createPersonByIdQuery(id);
        var users = ldapTemplate.search(query, new UserAttributesMapper());
        //List<User> users = this.users.stream().filter(user -> user.getId().equals(id)).collect(Collectors.toList());
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public Optional<List<User>> findByName(String name) {
        var query = ldapQueryFactory.createPersonByNameQuery(name);
        var users = ldapTemplate.search(query, new UserAttributesMapper());
//        List<User> users = this.users.stream().filter(
//                        user -> name.contains(user.getFirstname()) || name.contains(user.getSurname()))
//                .collect(Collectors.toList());
        return users.isEmpty() ? Optional.empty() : Optional.of(users);
    }
}