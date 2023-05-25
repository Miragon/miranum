package io.miranum.platform.tasklist.application.port.out.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
        super("User with id " + userId + " could not be found.");
    }
}
