package io.miragon.miranum.integrations.example.api.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTO {

    private String username;

    private String id;

    private String firstname;

    private String surname;

    private String email;

    private String title;

    private String department;

    private List<String> groups;
}
