package io.miragon.miranum.platform.security.authentication;

import org.springframework.lang.NonNull;

import java.util.List;

public interface UserAuthenticationProvider {

    @NonNull
    String getLoggedInUser();

    List<String> getLoggedInUserRoles();
}
