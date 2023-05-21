package io.miranum.platform.tasklist.adapter.out.user.easyldap;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User Info Response from easyLDAP.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInfoResponse {
    /**
     * Unique id of the user.
     */
    @JsonProperty("lhmObjectId")
    private String lhmObjectId;

    /**
     * User org unit.
     */
    @JsonProperty("ou")
    private String organizationalUnit;

    /**
     * List of user group and the superior organizational units as a string.
     */
    @JsonProperty("lhmObjectPath")
    private String lhmObjectPath;

    /**
     * First name of the user.
     */
    @JsonProperty("vorname")
    private String firstName;

    /**
     * Last name of the user.
     */
    @JsonProperty("nachname")
    private String lastName;

}
