package io.miragon.miranum.connect.worker.api;


public class ToManyParametersExecption extends RuntimeException {

    public ToManyParametersExecption(final Worker useCase) {
        super(String.format("To many parameters in use case %s . Only one is allowed", useCase.type()));
    }

}
