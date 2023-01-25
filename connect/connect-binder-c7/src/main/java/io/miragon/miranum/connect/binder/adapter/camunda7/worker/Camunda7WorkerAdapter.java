package io.miragon.miranum.connect.binder.adapter.camunda7.worker;

import io.miragon.miranum.connect.binder.adapter.camunda7.common.Camunda7Mapper;
import io.miragon.miranum.connect.binder.worker.application.port.in.ExecuteMethodCommand;
import io.miragon.miranum.connect.binder.worker.application.port.in.ExecuteMethodUseCase;
import io.miragon.miranum.connect.binder.worker.application.port.out.BindWorkerPort;
import io.miragon.miranum.connect.binder.worker.domain.BusinessException;
import io.miragon.miranum.connect.binder.worker.domain.TechnicalException;
import io.miragon.miranum.connect.binder.worker.domain.WorkerInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;

import java.util.Arrays;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class Camunda7WorkerAdapter implements BindWorkerPort {

    private final ExternalTaskClient externalTaskClient;
    private final Camunda7Mapper camunda7Mapper;
    private final ExecuteMethodUseCase executeMethodUseCase;

    @Override
    public void bind(final WorkerInfo workerInfo) {

        this.externalTaskClient.subscribe(workerInfo.getType())
                .lockDuration(workerInfo.getTimeout())
                .handler((task, service) -> this.execute(task, service, workerInfo))
                .open();
    }

    /**
     * Executes a task with a specific use case
     *
     * @param externalTask Task that should be executed
     * @param service      Task service to interact with
     * @param workerInfo   worker info that executes the tasks
     */
    public void execute(final ExternalTask externalTask, final ExternalTaskService service, final WorkerInfo workerInfo) {
        final Object value = this.camunda7Mapper.mapInput(workerInfo.getInputType(), externalTask.getAllVariablesTyped());
        try {
            //1. execute method
            final Object result = this.executeMethodUseCase.execute(new ExecuteMethodCommand(value, workerInfo));
            //2. convert to result map
            final Map<String, Object> resultMap = this.camunda7Mapper.mapOutput(result);
            //3. complete task
            service.complete(externalTask, null, resultMap);
        } catch (final BusinessException exception) {
            log.error("use case could not be executed", exception);
            service.handleBpmnError(externalTask, exception.getCode());
        } catch (final TechnicalException error) {
            log.error("Something went wrong", error);
            service.handleFailure(externalTask, error.getMessage(), Arrays.toString(error.getStackTrace()), 0, 0L);
        } catch (final Exception error) {
            log.error("Something went wrong", error);
            service.handleFailure(externalTask, error.getMessage(), Arrays.toString(error.getStackTrace()), externalTask.getRetries() - 1, 5000L);
        }
    }

}
