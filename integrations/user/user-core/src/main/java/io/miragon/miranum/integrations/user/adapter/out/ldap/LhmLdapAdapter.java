package io.miragon.miranum.integrations.user.adapter.out.ldap;

import io.miragon.miranum.integrations.user.adapter.out.ldap.mapper.LdapAttributeConstants;
import io.miragon.miranum.integrations.user.adapter.out.ldap.mapper.UserAttributesMapper;
import io.miragon.miranum.integrations.user.adapter.out.ldap.query.LdapQueryFactory;
import io.miragon.miranum.integrations.user.application.port.out.LoadOuTreePort;
import io.miragon.miranum.integrations.user.application.port.out.LoadUserPort;
import io.miragon.miranum.integrations.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.ldap.LdapName;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The {@link LdapTemplate} to query the ldap directory.
 * <p>
 * The LHM ldap tree is divided into two seperate sections, a data section (for persons/groups etc.) and an orga section (for orga units).
 * Each section has a different base, "o=Landeshauptstadt München,c=de" for orga and "o=lhm,c=de" for data.
 */
@Slf4j
public class LhmLdapAdapter extends LdapTemplate implements LoadOuTreePort, LoadUserPort {

    public static final String CACHE_USERS = "ldapusers";
    public static final String CACHE_USERBYNAME = "ldapuserbyname";
    public static final String CACHE_OUTREE = "ldapoutree";
    public static final String CACHE_OUS = "ldapous";
    private static final Logger LOG = LoggerFactory.getLogger(LhmLdapAdapter.class);
    private static final String LDAP_TYPE_OU = "ou";

    private final LdapQueryFactory ldapQueryFactory;

    public LhmLdapAdapter(final ContextSource contextSource, final LdapQueryFactory ldapQueryFactory) {
        super(contextSource);
        this.ldapQueryFactory = ldapQueryFactory;
    }

    /**
     * The method queries for user within the LDAP-Directory
     *
     * @param id The uid for which the search within LDAP is performed.
     * @return Returns the found object with the uid username as a {@link User} if only one user is found otherwise null.
     */
    @Override
    @Cacheable(CACHE_USERS)
    public Optional<User> findById(final String id) {
        LOG.debug("Get LDAP information for user {}.", id);
        final LdapQuery query = ldapQueryFactory.createPersonByIdQuery(id);

        val userList = super.search(query, new UserAttributesMapper());
        if (userList.size() == 1) {
            return Optional.ofNullable(userList.get(0));
        }

        if (userList.isEmpty()) {
            LOG.warn("No user with lhmObjId {} in LDAP found.", id);
            return Optional.empty();
        }

        LOG.warn("More than one user with lhmObjId {} in LDAP found.", id);
        return Optional.empty();
    }

    /**
     * The method queries for the user within the LDAP-Directory
     *
     * @param username The uid for which the search within LDAP is performed.
     * @return Returns the found object with the uid username as a {@link User} if only one user is found otherwise null.
     */
    @Override
    @Cacheable(CACHE_USERBYNAME)
    public Optional<User> findByUsername(final String username) {
        LOG.debug("Get LDAP information for user {}.", username);
        final LdapQuery query = ldapQueryFactory.createPersonByUsernameQuery(username);
        val userList = super.search(query, new UserAttributesMapper());
        if (userList.size() == 1) {
            return Optional.ofNullable(userList.get(0));
        }

        if (userList.isEmpty()) {
            LOG.warn("No user with username {} in LDAP found.", username);
            return Optional.empty();
        }

        LOG.warn("More than one user with username {} in LDAP found.", username);
        return Optional.empty();
    }

    /**
     * This method searches for users in the ldap for the given filter and ou's.
     *
     * @param expression filter to search in surname and givenname
     * @param ouFilters  ou's to limit the search result
     * @return user
     */
    @Override
    @Cacheable(CACHE_USERS)
    public List<User> findByNamesLike(final String expression, final List<String> ouFilters) {
        LOG.debug("Get LDAP information for name expression {}.", expression);
        final LdapQuery query = ldapQueryFactory.createPersonByNamePatternAndOuQuery(expression, ouFilters);
        return super.search(query, new UserAttributesMapper());
    }

    /**
     * This methods searches for users in the ldap by the given filters and ou's.
     *
     * @param firstExpression  Filter to search for in surname or givenname, connected with or
     * @param secondExpression Filter to search for in surname or givenname, connected with or
     * @param ouFilters        ou's to limit the search result
     * @return users
     */
    @Override
    @Cacheable(CACHE_USERS)
    public List<User> findByNamesLike(final String firstExpression, final String secondExpression, final List<String> ouFilters) {
        LOG.debug("Get LDAP information for users with {} {}.", firstExpression, secondExpression);
        final LdapQuery query = ldapQueryFactory.createPersonByNamePatternsAndOuQuery(firstExpression, secondExpression, ouFilters);
        return super.search(query, new UserAttributesMapper());
    }

    /**
     * The method queries for all ous the specified user is part of.
     *
     * @param userid The uid for which the search within LDAP is performed.
     * @return a list of ous the user is in
     */
    @Override
    @Cacheable(CACHE_OUTREE)
    public List<String> findOuTree(final String userid) {
        LOG.debug("Get LDAP ou tree for user {}.", userid);

        final List<LdapName> usersObjectPathList = this.searchObjectPaths(userid);

        if (usersObjectPathList.size() > 1) {
            log.warn("More than one entry found for user: {}", userid);
        }

        if (usersObjectPathList.size() == 0) {
            throw new IllegalArgumentException(String.format("OU tree for user with id %s not available", userid));
        }

        final List<String> ouTreeItems = initOuTreeItems();
        // take first user
        final LdapName ldapName = usersObjectPathList.get(0);

        for (int i = 0; i < ldapName.getRdns().size(); i++) {
            if (!ldapName.getRdn(i).getType().equals(LDAP_TYPE_OU)) {
                log.debug("Ignoring ldap objectpath entry: {}", ldapName.getRdn(i));
                continue;
            }
            final List<String> ouShortnames = searchOuShortnames(ldapName, i);
            if (!ouShortnames.isEmpty()) {
                ouTreeItems.addAll(ouShortnames);
            }
        }
        log.info("OU tree for user {}: {}", userid, String.join(", ", ouTreeItems));
        return ouTreeItems;
    }

    /**
     * Find ou by its short name.
     *
     * @param shortName shortname of the ou
     * @return the resulting ou
     */
    @Override
    @Cacheable(CACHE_OUS)
    public Optional<User> findOuByShortName(final String shortName) {
        LOG.debug("Get LDAP ou of {}.", shortName);
        final LdapQuery query = ldapQueryFactory.createOuByShortNameQuery(shortName);
        final List<User> ous = super.search(query, new UserAttributesMapper());

        if (ous.size() == 1) {
            return Optional.ofNullable(ous.get(0));
        }

        if (ous.isEmpty()) {
            LOG.warn("No ou with shortname {} in LDAP found.", shortName);
            return Optional.empty();
        }

        LOG.warn("More than one ou with shortname {} in LDAP found.", shortName);
        return Optional.empty();
    }

    private List<String> searchOuShortnames(final LdapName ldapName, final int rdnIndex) {
        final String ou = ldapName.getRdn(rdnIndex).getValue().toString();
        final String parentPath = this.buildObjectPath(ldapName, rdnIndex - 1);
        final List<String> ouShortnames = this.searchOusShortname(ou, parentPath);
        if (ouShortnames != null && !ouShortnames.isEmpty()) {
            // remove enties without value
            final List<String> cleanedOuShortnames = ouShortnames.stream().filter(Objects::nonNull).collect(Collectors.toList());
            if (cleanedOuShortnames.size() > 1) {
                log.warn("More than 1 ou for {} in {}", ou, parentPath);
            }
            return cleanedOuShortnames;
        } else {
            log.warn("No ou found for {} in {}", ou, parentPath);
        }
        return Collections.emptyList();
    }

    private List<String> initOuTreeItems() {
        final List<String> ouTreeItems = new ArrayList<>();
        ouTreeItems.add("LHM");
        return ouTreeItems;
    }

    private List<String> searchOusShortname(final String ou, final String parentPath) {
        final LdapQuery query = ldapQueryFactory.createOuByNameAndParentPathQuery(ou, parentPath);

        return this.search(query, (AttributesMapper<String>) attrs -> {
            if (null != attrs.get(LdapAttributeConstants.LDAP_SHORTNAME)) {
                return (String) attrs.get(LdapAttributeConstants.LDAP_SHORTNAME).get();
            }
            return null;
        });

    }

    /**
     * Search object path of specified user.
     */
    private List<LdapName> searchObjectPaths(final String lhmObjectId) {
        final LdapQuery query = ldapQueryFactory.createPersonByObjectIdQuery(lhmObjectId);
        return this.search(query, (AttributesMapper<LdapName>) attrs -> new LdapName((String) attrs.get(LdapAttributeConstants.LDAP_OBJECT_PATH).get()));
    }

    /**
     * Build an ldap object path string out of a given number of rdns.
     */
    private String buildObjectPath(final LdapName ldapName, final int pos) {
        final StringBuilder buf = new StringBuilder();
        for (int i = pos; i >= 0; i--) {
            if (buf.length() > 0)
                buf.append(",");
            buf.append(ldapName.get(i));
        }
        return buf.toString();
    }

}
