package io.miragon.miranum.integrations.user.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class User {

    private String username;

    private String id;

    private String forename;

    private String surname;

    private String email;

    private String title;

    private String department;

    private List<String> groups;
}

