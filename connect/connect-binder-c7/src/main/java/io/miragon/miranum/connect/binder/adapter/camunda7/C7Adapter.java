package io.miragon.miranum.connect.binder.adapter.camunda7;

import io.miragon.miranum.connect.binder.application.port.in.ExecuteMethodUseCase;
import io.miragon.miranum.connect.binder.application.port.out.BindUseCasePort;
import io.miragon.miranum.connect.binder.domain.BusinessException;
import io.miragon.miranum.connect.binder.domain.UseCaseInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.ExternalTaskClient;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class C7Adapter implements BindUseCasePort {

    private final ExternalTaskClient externalTaskClient;
    private final Camunda7Mapper camunda7Mapper;
    private final ExecuteMethodUseCase executeMethodUseCase;

    @Override
    public void bind(final UseCaseInfo useCaseInfo) {
        this.externalTaskClient.subscribe(useCaseInfo.getType())
                .handler((externalTask, externalTaskService) -> {
                    //1. convert camunda data into object
                    final Object value = this.camunda7Mapper.convertInput(useCaseInfo.getInputType()[0], externalTask.getAllVariablesTyped());
                    try {
                        //2. execute method
                        final Object result = this.executeMethodUseCase.execute(value, useCaseInfo);
                        //3. convert to result map
                        final Map<String, Object> resultMap = this.camunda7Mapper.convertOutput(result);
                        externalTaskService.complete(externalTask, null, resultMap);
                    } catch (final BusinessException exception) {
                        log.error("use case could not be executed", exception);
                        externalTaskService.handleBpmnError(externalTask, exception.getCode());
                    } catch (final Exception error) {
                        log.error("Something went wrong", error);
                        //todo retry cycle
                    }
                }).open();
    }

}
