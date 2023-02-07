package io.miragon.miranum.integrations.user.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String username;

    private String id;

    private String firstname;

    private String surname;

    private String email;

    private String title;

    private String department;

    private List<String> groups;
}

