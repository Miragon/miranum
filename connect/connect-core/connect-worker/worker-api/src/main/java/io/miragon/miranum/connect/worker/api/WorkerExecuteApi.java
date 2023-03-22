package io.miragon.miranum.connect.worker.api;

import io.miragon.miranum.connect.worker.impl.WorkerExecutor;

/**
 * Interface to execute a worker.
 */
public interface WorkerExecuteApi {

    /**
     * Registers a worker.
     *
     * @param workerExecutor worker to be registered
     */
    void register(final WorkerExecutor workerExecutor);

    /**
     * Executes a worker.
     *
     * @param data data to be processed
     * @param type type of the worker
     * @return result of the worker
     */
    Object execute(final String type, final Object data);

}
