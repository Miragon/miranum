package io.miragon.miranum.connect.binder.camunda7;

import io.miragon.miranum.connect.binder.domain.UseCase;

public class MyJobWorker {

    @UseCase(type = "doJob")
    public Output doJob(final Input input) {
        return null;
    }
}
