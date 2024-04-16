package io.miragon.miranum.platform.connect.worker.worker.api;

import io.miragon.miranum.platform.connect.worker.worker.impl.WorkerExecutor;

/**
 * Interface to execute a worker.
 */
public interface WorkerRegistry {

    /**
     * Registers a worker.
     *
     * @param workerExecutor worker to be registered
     */
    void register(final WorkerExecutor workerExecutor);
}
