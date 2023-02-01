package io.miragon.miranum.integrations.example.ldap.mapper;

import io.miragon.miranum.integrations.user.domain.User;
import org.springframework.ldap.core.AttributesMapper;

import javax.naming.directory.Attributes;

/**
 * The Attributes mapper which is used to map the found attributes to a {@link User}.
 */
public class UserAttributesMapper implements AttributesMapper<User> {

    /**
     * The method maps the given ldap {@link Attributes} to the {@link User}.
     *
     * @param attrs The ldap {@link Attributes} for a uid.
     * @return The consolidated {@link Attributes} as a {@link User}.
     */
    @Override
    public User mapFromAttributes(final Attributes attrs) {
        final User user = new User();
        try {
            user.setUsername((String) attrs.get(LdapAttributeConstants.LDAP_UID).get());
        } catch (final Exception e) {
            user.setUsername(LdapAttributeConstants.LDAP_NOATTRIBUTEAVAILABLE);
        }
        try {
            user.setId((String) attrs.get(LdapAttributeConstants.LDAP_OBJID).get());
        } catch (final Exception e) {
            user.setId(LdapAttributeConstants.LDAP_NOATTRIBUTEAVAILABLE);
        }
        try {
            user.setForename((String) attrs.get(LdapAttributeConstants.LDAP_GIVENNAME).get());
        } catch (final Exception e) {
            user.setForename(LdapAttributeConstants.LDAP_NOATTRIBUTEAVAILABLE);
        }
        try {
            user.setSurname((String) attrs.get(LdapAttributeConstants.LDAP_SURNAME).get());
        } catch (final Exception e) {
            user.setSurname(LdapAttributeConstants.LDAP_NOATTRIBUTEAVAILABLE);
        }
        try {
            user.setEmail((String) attrs.get(LdapAttributeConstants.LDAP_EMAIL).get());
        } catch (final Exception e) {
            user.setEmail(LdapAttributeConstants.LDAP_NOATTRIBUTEAVAILABLE);
        }
        try {
            user.setCn((String) attrs.get(LdapAttributeConstants.LDAP_CN).get());
        } catch (final Exception e) {
            user.setCn(LdapAttributeConstants.LDAP_NOATTRIBUTEAVAILABLE);
        }
        try {
            user.setLhmOfficePostalCode((String) attrs.get(LdapAttributeConstants.LDAP_LHMOFFICEPOSTALCODE).get());
        } catch (final Exception e) {
            user.setLhmOfficePostalCode(LdapAttributeConstants.LDAP_NOATTRIBUTEAVAILABLE);
        }
        try {
            user.setLhmOfficeStreetAddress((String) attrs.get(LdapAttributeConstants.LDAP_LHMOFIICESTREETADDRESS).get());
        } catch (final Exception e) {
            user.setLhmOfficeStreetAddress(LdapAttributeConstants.LDAP_NOATTRIBUTEAVAILABLE);
        }
        try {
            user.setLhmOULongname((String) attrs.get(LdapAttributeConstants.LDAP_LHMOULONGNAME).get());
        } catch (final Exception e) {
            user.setLhmOULongname(LdapAttributeConstants.LDAP_NOATTRIBUTEAVAILABLE);
        }
        try {
            user.setLhmTitle((String) attrs.get(LdapAttributeConstants.LDAP_LHMTITLE).get());
        } catch (final Exception e) {
            user.setLhmTitle(LdapAttributeConstants.LDAP_NOATTRIBUTEAVAILABLE);
        }
        try {
            user.setOu((String) attrs.get(LdapAttributeConstants.LDAP_OU).get());
        } catch (final Exception e) {
            user.setOu(LdapAttributeConstants.LDAP_NOATTRIBUTEAVAILABLE);
        }
        try {
            user.setPostalCode((String) attrs.get(LdapAttributeConstants.LDAP_POSTALCODE).get());
        } catch (final Exception e) {
            user.setPostalCode(LdapAttributeConstants.LDAP_NOATTRIBUTEAVAILABLE);
        }
        try {
            user.setRoomNumber((String) attrs.get(LdapAttributeConstants.LDAP_ROOMNUMBER).get());
        } catch (final Exception e) {
            user.setRoomNumber(LdapAttributeConstants.LDAP_NOATTRIBUTEAVAILABLE);
        }
        try {
            user.setStreet((String) attrs.get(LdapAttributeConstants.LDAP_STREET).get());
        } catch (final Exception e) {
            user.setStreet(LdapAttributeConstants.LDAP_NOATTRIBUTEAVAILABLE);
        }
        try {
            user.setTelephoneNumber((String) attrs.get(LdapAttributeConstants.LDAP_TELEPHONENUMBER).get());
        } catch (final Exception e) {
            user.setTelephoneNumber(LdapAttributeConstants.LDAP_NOATTRIBUTEAVAILABLE);
        }
        try {
            user.setFacsimileTelephoneNumber((String) attrs.get(LdapAttributeConstants.LDAP_FACTELEPHONENUMBER).get());
        } catch (final Exception e) {
            user.setFacsimileTelephoneNumber(LdapAttributeConstants.LDAP_NOATTRIBUTEAVAILABLE);
        }
        try {
            user.setTitle((String) attrs.get(LdapAttributeConstants.LDAP_TITLE).get());
        } catch (final Exception e) {
            user.setTitle(LdapAttributeConstants.LDAP_NOATTRIBUTEAVAILABLE);
        }
        try {
            user.setDepartment((String) attrs.get(LdapAttributeConstants.LDAP_DEPARTMENT).get());
        } catch (final Exception e) {
            user.setDepartment(LdapAttributeConstants.LDAP_NOATTRIBUTEAVAILABLE);
        }

        return user;
    }

}
