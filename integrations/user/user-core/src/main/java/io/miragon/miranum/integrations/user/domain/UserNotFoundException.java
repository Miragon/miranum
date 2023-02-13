package io.miragon.miranum.integrations.user.domain;

import io.miragon.miranum.connect.worker.api.TechnicalException;

public class UserNotFoundException extends TechnicalException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Exception exception) {
        super(exception);
    }
}
