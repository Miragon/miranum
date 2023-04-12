package io.miragon.miranum.connect.worker.api;

import io.miragon.miranum.connect.worker.impl.WorkerExecutor;

public interface WorkerSubscription {

    void subscribe(WorkerExecutor executor);

}
