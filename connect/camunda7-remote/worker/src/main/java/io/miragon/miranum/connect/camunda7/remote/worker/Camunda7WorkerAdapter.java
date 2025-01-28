package io.miragon.miranum.connect.camunda7.remote.worker;

import io.miragon.miranum.connect.camnda7.remote.utils.Camunda7PojoMapper;
import io.miragon.miranum.connect.worker.api.BusinessException;
import io.miragon.miranum.connect.worker.api.TechnicalException;
import io.miragon.miranum.connect.worker.api.WorkerExecuteApi;
import io.miragon.miranum.connect.worker.api.WorkerSubscription;
import io.miragon.miranum.connect.worker.impl.WorkerExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;

import java.util.Arrays;
import java.util.Map;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Slf4j
public class Camunda7WorkerAdapter implements WorkerSubscription {

    private final ExternalTaskClient externalTaskClient;
    private final WorkerExecuteApi workerExecuteApi;
    private final Camunda7PojoMapper camunda7PojoMapper;
    private final Camunda7WorkerProperties camunda7WorkerProperties;

    @Override
    public void subscribe(final WorkerExecutor executor) {
        this.externalTaskClient.subscribe(executor.getType())
                .lockDuration(executor.getTimeout())
                .handler((externalTask, externalTaskService) -> this.execute(executor, externalTask, externalTaskService))
                .open();
    }

    public void execute(final WorkerExecutor executor, final ExternalTask externalTask, final ExternalTaskService service) {
        Integer workerRetries = null;
        try {
            final Map<String, Object> data = camunda7PojoMapper.mapFromEngineData(externalTask.getAllVariablesTyped());
            log.debug("Worker {} called with parameters {}", executor.getType(), data);
            workerRetries = (Integer) data.get("retries");
            final Map<String, Object> result = this.workerExecuteApi.execute(executor, data);
            service.complete(externalTask, null, camunda7PojoMapper.mapToEngineData(result));
        } catch (final BusinessException exception) {
            log.warn("Use case could not be executed {}", exception.getMessage());
            service.handleBpmnError(externalTask, exception.getCode(), exception.getMessage());
        } catch (final TechnicalException error) {
            log.warn("Technical error while executing task {}", error.getMessage());
            service.handleFailure(externalTask, error.getMessage(), Arrays.toString(error.getStackTrace()), 0, 0L);
        } catch (final Exception error) {
            int retries = getRemainingRetries(externalTask.getRetries(), workerRetries);
            log.error("Error while executing external task {}", error.getMessage(), error);
            service.handleFailure(externalTask, error.getMessage(), Arrays.toString(error.getStackTrace()), retries, 5000L);
        }
    }

    /**
     * Retrieves the remaining number of retries for a task.
     * <p>
     * If it's the first run and no retries input is provided,
     * the method uses the default retries specified in the properties.
     * <p>
     * For subsequent runs, where externalTaskRetries is not null, it is used.
     *
     * @param externalTaskRetries The number of retries specified for the external task.
     * @param workerRetries       The number of retries specified for the worker as an input in the bpmn.
     * @return The remaining number of retries for the task.
     */
    private int getRemainingRetries(Integer externalTaskRetries, Integer workerRetries) {
        int retries;

        if (nonNull(externalTaskRetries)) {
            retries = externalTaskRetries;
        } else if (nonNull(workerRetries)) {
            retries = workerRetries;
        } else {
            retries = camunda7WorkerProperties.getDefaultRetries();
        }

        return Math.max(retries - 1, 0);
    }
}
