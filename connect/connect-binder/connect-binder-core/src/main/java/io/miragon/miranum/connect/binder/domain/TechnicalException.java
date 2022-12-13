package io.miragon.miranum.connect.binder.domain;

public class TechnicalException extends RuntimeException {

    public TechnicalException(final String message) {
        super(message);
    }

    public TechnicalException(final Exception exception) {
        super(exception);
    }

}
