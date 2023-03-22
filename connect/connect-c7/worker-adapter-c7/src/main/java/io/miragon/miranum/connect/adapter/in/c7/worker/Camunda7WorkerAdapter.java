package io.miragon.miranum.connect.adapter.in.c7.worker;

import io.miragon.miranum.connect.worker.api.BusinessException;
import io.miragon.miranum.connect.worker.api.TechnicalException;
import io.miragon.miranum.connect.worker.api.WorkerExecuteApi;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class Camunda7WorkerAdapter {

    private final ExternalTaskClient externalTaskClient;
    private final WorkerExecuteApi workerExecuteApi;

    @PostConstruct
    public void init() {
        // external task client subscribe to each available worker type
        this.workerExecuteApi.availableWorkerExecutors()
            .forEach(workerExecutor -> {
                this.externalTaskClient.subscribe(workerExecutor.getType())
                        .lockDuration(workerExecutor.getTimeout())
                        .handler((task, service) -> this.execute(task, service))
                        .open();
            });
    }

    /**
     * Executes a task with a specific use case
     *
     * @param externalTask Task that should be executed
     * @param service      Task service to interact with
     */
    public void execute(final ExternalTask externalTask, final ExternalTaskService service) {
        try {
            final Map<String, Object> result = this.workerExecuteApi.execute(externalTask.getTopicName(), externalTask.getAllVariablesTyped());
            service.complete(externalTask, null, result);
        } catch (final BusinessException exception) {
            log.error("use case could not be executed", exception);
            service.handleBpmnError(externalTask, exception.getCode());
        } catch (final TechnicalException error) {
            log.error("Technical error while executing task", error);
            service.handleFailure(externalTask, error.getMessage(), Arrays.toString(error.getStackTrace()), 0, 0L);
        } catch (final Exception error) {
            log.error("Error while executing external task", error);
            final int retries = Objects.isNull(externalTask.getRetries()) ? 1 : externalTask.getRetries();
            service.handleFailure(externalTask, error.getMessage(), Arrays.toString(error.getStackTrace()), retries - 1, 5000L);
        }
    }

}
