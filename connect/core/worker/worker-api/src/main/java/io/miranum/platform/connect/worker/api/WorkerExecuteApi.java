package io.miranum.platform.connect.worker.api;

import io.miranum.platform.connect.worker.impl.WorkerExecutor;

import java.util.Map;

/**
 * Interface to execute a worker.
 */
public interface WorkerExecuteApi {

    /**
     * Executes a worker.
     *
     * @param executor executor of the worker
     * @param data     data to be processed
     * @return result of the worker
     */
    Map<String, Object> execute(final WorkerExecutor executor, final Object data);
}