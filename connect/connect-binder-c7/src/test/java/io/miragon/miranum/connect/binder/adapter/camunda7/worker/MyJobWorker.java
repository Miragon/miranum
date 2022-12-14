package io.miragon.miranum.connect.binder.adapter.camunda7.worker;

import io.miragon.miranum.connect.binder.domain.Worker;

public class MyJobWorker {

    @Worker(type = "doJob")
    public Output doJob(final Input input) {
        return null;
    }
}
