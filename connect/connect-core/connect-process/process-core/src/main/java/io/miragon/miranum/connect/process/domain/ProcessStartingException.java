package io.miragon.miranum.connect.process.domain;

public class ProcessStartingException extends RuntimeException {

    public ProcessStartingException(final String message, final Exception e) {
        super(message, e);
    }

    public ProcessStartingException(final String message) {
        super(message);
    }
}
