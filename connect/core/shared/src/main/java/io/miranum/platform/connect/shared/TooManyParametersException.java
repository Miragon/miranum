package io.miranum.platform.connect.shared;

import java.lang.annotation.Annotation;

public class TooManyParametersException extends RuntimeException {

    public TooManyParametersException(final Annotation annotation) {
        super(String.format("Too many parameters in worker %s . Only one is allowed", annotation.annotationType()));
    }
}