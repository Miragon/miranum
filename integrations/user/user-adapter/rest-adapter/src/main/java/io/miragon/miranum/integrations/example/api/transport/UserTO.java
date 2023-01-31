package io.miragon.miranum.integrations.example.api.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User transport object.
 *
 * @author externer.dl.horn
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTO {

    /**
     * unique id of the user
     */
    private String lhmObjectId;

    /**
     * Username
     */
    private String username;

    /**
     * Forname
     */
    private String forename;

    /**
     * Surname
     */
    private String surname;

    /**
     * Group of the user
     */
    private String ou;

    /**
     * Department of the user
     */
    private String department;

    /**
     * Email address
     */
    private String email;

}
