package io.miragon.miranum.connect.camunda7.remote.worker.worker;


import io.miragon.miranum.connect.worker.api.Worker;

public class MyJobWorker {

    @Worker(type = "doJob")
    public Output doJob(final Input input) {
        return null;
    }
}
