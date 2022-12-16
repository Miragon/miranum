package io.miragon.miranum.connect.binder.adapter.spring.worker;

import io.miragon.miranum.connect.binder.worker.domain.Worker;

public class ToManyParametersExecption extends RuntimeException {

    public ToManyParametersExecption(final Worker useCase) {
        super(String.format("To many parameters in use case %s . Only one is allowed", useCase.type()));
    }

}
