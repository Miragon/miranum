package io.miragon.miranum.auth.api;

import java.util.List;

public interface UserAuthenticationProvider {

    String getLoggedInUser();

    List<String> getLoggedInUserRoles();
}
