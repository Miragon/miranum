package io.miragon.miranum.integrations.example.ldap.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constants of LHM ldap attributes.
 *
 * @author martin.dietrich
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LdapAttributeConstants {

    public static final String LDAP_GIVENNAME = "givenName";

    public static final String LDAP_SURNAME = "sn";

    public static final String LDAP_EMAIL = "mail";

    public static final String LDAP_CN = "cn";

    public static final String LDAP_LHMOFFICEPOSTALCODE = "lhmOfficePostalCode";

    public static final String LDAP_LHMOFIICESTREETADDRESS = "lhmOfficeStreetAddress";

    public static final String LDAP_LHMOULONGNAME = "lhmOULongname";

    public static final String LDAP_LHMTITLE = "lhmTitle";

    public static final String LDAP_OU = "ou";

    public static final String LDAP_POSTALCODE = "postalCode";

    public static final String LDAP_ROOMNUMBER = "roomNumber";

    public static final String LDAP_STREET = "street";

    public static final String LDAP_TELEPHONENUMBER = "telephoneNumber";

    public static final String LDAP_FACTELEPHONENUMBER = "facsimileTelephoneNumber";

    public static final String LDAP_NOATTRIBUTEAVAILABLE = "No_Attribute_Available";

    public static final String LDAP_UID = "uid";

    public static final String LDAP_OBJID = "lhmObjectId";

    public static final String LDAP_TITLE = "title";

    public static final String LDAP_DEPARTMENT = "lhmReferatName";

    public static final String LDAP_SHORTNAME = "lhmOuShortName";

    public static final String LDAP_OBJECT_CLASS = "objectclass";

    public static final String LDAP_OBJECT_PATH = "lhmObjectPath";


}
