package io.miragon.miranum.connect.adapter.in.c8.worker.worker;


import io.miragon.miranum.connect.worker.domain.Worker;

public class MyJobWorker {

    @Worker(type = "doJob")
    public Output doJob(final Input input) {
        return null;
    }
}
