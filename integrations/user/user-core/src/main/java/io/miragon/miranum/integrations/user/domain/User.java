package io.miragon.miranum.integrations.user.domain;

import io.miragon.miranum.integrations.user.adapter.out.ldap.mapper.UserAttributesMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * The class is used by the {@link UserAttributesMapper} to consolidate the found
 * ldap attributes.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class User {

    /**
     * username of the user.
     * Often used for logins.
     */
    private String username;

    /**
     * Unique id of the user.
     */
    private String lhmObjectId;

    /**
     * Forename
     */
    private String forename;

    /**
     * Surname
     */
    private String surname;

    /**
     * Emaill address
     */
    private String email;

    /**
     * TODO
     */
    private String cn;

    /**
     * Postal code of the office
     */
    private String lhmOfficePostalCode;

    /**
     * Street of the office
     */
    private String lhmOfficeStreetAddress;

    /**
     * Name of the user group
     */
    private String lhmOULongname;

    /**
     * Title of the person
     */
    private String lhmTitle;

    /**
     * User group
     */
    private String ou;

    /**
     * Postal Code of the user
     */
    private String postalCode;

    /**
     * Room number of the user
     */
    private String roomNumber;

    /**
     * Street of the user
     */
    private String street;

    /**
     * Telefphone number.
     */
    private String telephoneNumber;

    /**
     * TODO
     */
    private String facsimileTelephoneNumber;

    /**
     * Verifies if the user is enabled or not.
     */
    private boolean userEnabled;

    /**
     * TODO
     */
    private Set<String> authorities;

    /**
     * Title of the position
     */
    private String title;

    /**
     * Department of the user
     */
    private String department;

}

