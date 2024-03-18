package io.miranum.platform.user.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String username;

    private String forename;

    private String surname;

    private String email;

}

