package io.miranum.platform.connect.adapter.in.c7.worker;

import io.miranum.platform.connect.c7.utils.Camunda7PojoMapper;
import io.miranum.platform.connect.worker.api.BusinessException;
import io.miranum.platform.connect.worker.api.TechnicalException;
import io.miranum.platform.connect.worker.api.WorkerExecuteApi;
import io.miranum.platform.connect.worker.api.WorkerSubscription;
import io.miranum.platform.connect.worker.impl.WorkerExecutor;
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
            workerRetries = (Integer) data.get("retries");
            final Map<String, Object> result = this.workerExecuteApi.execute(executor, data);
            service.complete(externalTask, null, camunda7PojoMapper.mapToEngineData(result));
        } catch (final BusinessException exception) {
            log.severe("use case could not be executed " + exception.getMessage());
            service.handleBpmnError(externalTask, exception.getCode(), exception.getMessage());
        } catch (final TechnicalException error) {
            log.severe("Technical error while executing task " + error.getMessage());
            service.handleFailure(externalTask, error.getMessage(), Arrays.toString(error.getStackTrace()), 0, 0L);
        } catch (final Exception error) {
            int retries = getRemainingRetries(externalTask.getRetries(), workerRetries);
            log.severe("Error while executing external task " + error.getMessage());
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
        int retries = 0;
        if (Objects.isNull(externalTaskRetries)) {
            retries = Objects.isNull(workerRetries) ?
                    camunda7WorkerProperties.getDefaultRetries() :
                    workerRetries;
        } else {
            retries = externalTaskRetries;
        }
        retries -= 1;
        return Math.max(retries, 0);
    }
}
