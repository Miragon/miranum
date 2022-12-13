package io.miragon.miranum;

import io.miragon.miranum.connect.binder.domain.UseCase;

public class MyJobWorker {

    @UseCase(type = "myJob")
    public Output doJob(final Input input) {
        return null;
    }
}
