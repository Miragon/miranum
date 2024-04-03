package io.miranum.platform.connect.worker.api;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final String code;

    public BusinessException(final String code, final String message) {
        super(message);
        this.code = code;
    }
}