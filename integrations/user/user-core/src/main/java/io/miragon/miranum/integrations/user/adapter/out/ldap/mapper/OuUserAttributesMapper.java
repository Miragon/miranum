package io.miragon.miranum.integrations.user.adapter.out.ldap.mapper;

import io.miragon.miranum.integrations.user.domain.OuUserInfo;
import org.springframework.ldap.core.AttributesMapper;

import javax.naming.directory.Attributes;

/**
 * The Attributes mapper which is used to map the found attributes to {@link OuUserInfo}.
 */
public class OuUserAttributesMapper implements AttributesMapper<OuUserInfo> {

    /**
     * The method maps the given ldap {@link Attributes} to the {@link OuUserInfo}.
     *
     * @param attrs The ldap {@link Attributes} for a uid.
     * @return The uid from the {@link Attributes} as a {@link OuUserInfo}.
     */
    @Override
    public OuUserInfo mapFromAttributes(final Attributes attrs) {
        final OuUserInfo ou = new OuUserInfo();
        try {
            ou.setUid((String) attrs.get(LdapAttributeConstants.LDAP_UID).get());
        } catch (final Exception e) {
            ou.setUid(LdapAttributeConstants.LDAP_NOATTRIBUTEAVAILABLE);
        }
        return ou;
    }

}

