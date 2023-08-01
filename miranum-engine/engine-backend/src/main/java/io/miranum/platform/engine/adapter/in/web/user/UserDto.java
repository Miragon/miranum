package io.miranum.platform.engine.adapter.in.web.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String id;

    private String username;

    private String forename;

    private String surname;

    private String email;

}
