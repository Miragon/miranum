package io.miragon.miranum.connect.binder.adapter.in;

import io.miragon.miranum.connect.binder.domain.UseCase;

public class ToManyParametersExecption extends RuntimeException {

    public ToManyParametersExecption(final UseCase useCase) {
        super(String.format("To many parameters in use case %s . Only one is allowed", useCase.type()));
    }

}
