package io.miranum.platform.connect.worker.api;

import io.miranum.platform.connect.worker.impl.WorkerExecutor;

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
