package io.miragon.miranum.connect.binder.adapter.camunda7;

import io.miragon.miranum.connect.binder.application.port.in.ExecuteMethodUseCase;
import io.miragon.miranum.connect.binder.application.port.in.ExecuteUseCaseCommand;
import io.miragon.miranum.connect.binder.application.port.out.BindUseCasePort;
import io.miragon.miranum.connect.binder.domain.BusinessException;
import io.miragon.miranum.connect.binder.domain.TechnicalException;
import io.miragon.miranum.connect.binder.domain.UseCaseInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;

import java.util.Arrays;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
class C7Adapter implements BindUseCasePort {

    private final ExternalTaskClient externalTaskClient;
    private final Camunda7Mapper camunda7Mapper;
    private final ExecuteMethodUseCase executeMethodUseCase;

    @Override
    public void bind(final UseCaseInfo useCaseInfo) {
        this.externalTaskClient.subscribe(useCaseInfo.getType())
                .lockDuration(useCaseInfo.getTimeout())
                .handler((task, service) -> this.execute(task, service, useCaseInfo))
                .open();
    }

    /**
     * Executes a task with a specific use case
     *
     * @param externalTask Task that should be executed
     * @param service      Task service to interact with
     * @param useCaseInfo  usecase that executes the tasks
     */
    public void execute(final ExternalTask externalTask, final ExternalTaskService service, final UseCaseInfo useCaseInfo) {
        final Object value = this.camunda7Mapper.convertInput(useCaseInfo.getInputType(), externalTask.getAllVariablesTyped());
        try {
            //2. execute method
            final Object result = this.executeMethodUseCase.execute(new ExecuteUseCaseCommand(value, useCaseInfo));
            //3. convert to result map
            final Map<String, Object> resultMap = this.camunda7Mapper.convertOutput(result);
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
