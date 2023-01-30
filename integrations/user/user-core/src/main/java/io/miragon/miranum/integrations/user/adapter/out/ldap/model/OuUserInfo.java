package io.miragon.miranum.integrations.user.adapter.out.ldap.model;

import lombok.Data;

/**
 * The class is used to map the uid associated by the ou.
 *
 * @author externer.dl.horn
 */
@Data
public class OuUserInfo {

    /**
     * Uid (username) of the user.
     */
    private String uid;

}

