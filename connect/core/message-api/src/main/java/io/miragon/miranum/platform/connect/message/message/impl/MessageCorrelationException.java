package io.miragon.miranum.platform.connect.message.message.impl;

public class MessageCorrelationException extends RuntimeException {

    public MessageCorrelationException(final String message, final Exception e) {
        super(message, e);
    }

    public MessageCorrelationException(final String message) {
        super(message);
    }
}
