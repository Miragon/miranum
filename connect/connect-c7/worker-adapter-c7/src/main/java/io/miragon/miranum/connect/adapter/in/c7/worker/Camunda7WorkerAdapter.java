package io.miragon.miranum.connect.adapter.in.c7.worker;

import io.miragon.miranum.connect.worker.api.BusinessException;
import io.miragon.miranum.connect.worker.api.TechnicalException;
import io.miragon.miranum.connect.worker.api.WorkerExecuteApi;
import io.miragon.miranum.connect.worker.api.WorkerSubscription;
import io.miragon.miranum.connect.worker.impl.WorkerExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Log
public class Camunda7WorkerAdapter implements WorkerSubscription {

    private final ExternalTaskClient externalTaskClient;
    private final WorkerExecuteApi workerExecuteApi;

    @Override
    public void subscribe(final WorkerExecutor executor) {
        // external task client subscribe to each available worker type
        this.externalTaskClient.subscribe(executor.getType())
                .lockDuration(executor.getTimeout())
                .handler((externalTask, externalTaskService) -> this.execute(executor, externalTask, externalTaskService))
                .open();
    }

    /**
     * Executes a task with a specific use case
     *
     * @param externalTask Task that should be executed
     * @param service      Task service to interact with
     */
    public void execute(final WorkerExecutor executor, final ExternalTask externalTask, final ExternalTaskService service) {
        try {
            final Map<String, Object> result = this.workerExecuteApi.execute(executor, externalTask.getAllVariablesTyped());
            service.complete(externalTask, null, result);
        } catch (final BusinessException exception) {
            log.severe("use case could not be executed " + exception.getMessage());
            service.handleBpmnError(externalTask, exception.getCode());
        } catch (final TechnicalException error) {
            log.severe("Technical error while executing task " + error.getMessage());
            service.handleFailure(externalTask, error.getMessage(), Arrays.toString(error.getStackTrace()), 0, 0L);
        } catch (final Exception error) {
            log.severe("Error while executing external task " + error.getMessage());
            final int retries = Objects.isNull(externalTask.getRetries()) ? 1 : externalTask.getRetries();
            service.handleFailure(externalTask, error.getMessage(), Arrays.toString(error.getStackTrace()), retries - 1, 5000L);
        }
    }

}
