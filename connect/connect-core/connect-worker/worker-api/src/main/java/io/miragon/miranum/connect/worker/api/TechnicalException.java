package io.miragon.miranum.connect.worker.api;

public class TechnicalException extends RuntimeException {

    public TechnicalException(final String message) {
        super(message);
    }

    public TechnicalException(final Exception exception) {
        super(exception);
    }

}