package io.miragon.miranum.connect.shared;

import java.lang.annotation.Annotation;

public class ToManyParametersException extends RuntimeException {

    public ToManyParametersException(final Annotation annotation) {
        super(String.format("To many parameters in worker %s . Only one is allowed", annotation.annotationType()));
    }
}