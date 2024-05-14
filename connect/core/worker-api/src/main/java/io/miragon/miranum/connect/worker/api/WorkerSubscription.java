package io.miragon.miranum.connect.worker.api;

import io.miragon.miranum.connect.worker.impl.WorkerExecutor;

public interface WorkerSubscription {

    /**
     * Subscribes a worker to a topic.
     *
     * @param executor worker executor to be subscribed
     */
    void subscribe(WorkerExecutor executor);
}
