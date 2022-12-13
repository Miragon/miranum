package io.miragon.miranum.connect.binder.domain;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final String code;

    public BusinessException(final String code, final String message) {
        super(message);
        this.code = code;
    }
}
