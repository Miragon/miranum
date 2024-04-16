package io.miragon.miranum.platform.connect.process.process.api;

public class ProcessStartingException extends RuntimeException {

    public ProcessStartingException(final String message, final Exception e) {
        super(message, e);
    }

    public ProcessStartingException(final String message) {
        super(message);
    }
}
