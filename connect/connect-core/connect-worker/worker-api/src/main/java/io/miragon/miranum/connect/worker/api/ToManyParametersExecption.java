package io.miragon.miranum.connect.worker.api;


public class ToManyParametersExecption extends RuntimeException {

    public ToManyParametersExecption(final Worker worker) {
        super(String.format("To many parameters in worker %s . Only one is allowed", worker.type()));
    }

}
