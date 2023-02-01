package io.miragon.miranum.integrations.example.ldap.mapper;

import io.miragon.miranum.integrations.user.domain.User;
import org.springframework.ldap.core.AttributesMapper;

import javax.naming.directory.Attributes;
import java.util.List;

public class UserAttributesMapper implements AttributesMapper<User> {

    @Override
    public User mapFromAttributes(final Attributes attrs) {
        final User user = new User();

        try {
            user.setDepartment((String) attrs.get(LdapAttributeConstants.DEPARTMENT).get());
        } catch (final Exception ignored) {
        }
        try {
            user.setUsername((String) attrs.get(LdapAttributeConstants.USERNAME).get());
        } catch (final Exception ignored) {
        }
        try {
            user.setFirstname((String) attrs.get(LdapAttributeConstants.FIRSTNAME).get());
        } catch (final Exception ignored) {
        }
        try {
            user.setSurname((String) attrs.get(LdapAttributeConstants.SURNAME).get());
        } catch (final Exception ignored) {
        }
        try {
            user.setId((String) attrs.get(LdapAttributeConstants.ID).get());
        } catch (final Exception ignored) {
        }
        try {
            user.setTitle((String) attrs.get(LdapAttributeConstants.TITLE).get());
        } catch (final Exception ignored) {
        }
        try {
            user.setEmail((String) attrs.get(LdapAttributeConstants.EMAIL).get());
        } catch (final Exception ignored) {
        }
        try {
            String groups = (String) attrs.get(LdapAttributeConstants.GROUPS).get();
            user.setGroups(List.of(groups.split(",")));
        } catch (final Exception ignored) {
        }
        return user;
    }
}
