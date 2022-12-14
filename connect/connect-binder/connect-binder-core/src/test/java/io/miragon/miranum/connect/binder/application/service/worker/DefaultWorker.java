package io.miragon.miranum.connect.binder.application.service.worker;

import io.miragon.miranum.connect.binder.domain.Worker;

public class DefaultWorker {

    @Worker(type = "doSomething")
    public Output doSomething(final Input input) {
        return new Output("");
    }

    @Worker(type = "doVoid")
    public void doVoid(final Input input) {
        //
    }

}
