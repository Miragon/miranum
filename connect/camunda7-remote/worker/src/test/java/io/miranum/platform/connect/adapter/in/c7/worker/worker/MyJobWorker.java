package io.miranum.platform.connect.adapter.in.c7.worker.worker;


import io.miranum.platform.connect.worker.api.Worker;

public class MyJobWorker {

    @Worker(type = "doJob")
    public Output doJob(final Input input) {
        return null;
    }
}
