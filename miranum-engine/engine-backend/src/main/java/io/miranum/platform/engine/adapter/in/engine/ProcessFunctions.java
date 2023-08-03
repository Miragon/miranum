package io.miranum.platform.engine.adapter.in.engine;

import io.miranum.platform.engine.application.port.in.process.UpdateProcessInstanceDescriptionUseCase;
import io.miranum.platform.engine.application.port.in.process.UpdateProcessInstanceStatusUseCase;
import io.miranum.platform.engine.application.port.in.processconfig.ProcessConfigQuery;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.camunda.bpm.engine.impl.context.Context;
import org.springframework.stereotype.Component;


/**
 * Functions to simplify modeling.
 *
 * @author externer.dl.horn
 */
@Component("process")
@RequiredArgsConstructor
public class ProcessFunctions {

    private final ProcessConfigQuery processConfigQuery;
    private final UpdateProcessInstanceDescriptionUseCase updateProcessInstanceDescriptionUseCase;
    private final UpdateProcessInstanceStatusUseCase updateProcessInstanceStatusUseCase;

    public void setStatus(final String statusKey) {
        val processConfig = this.processConfigQuery.getByRef(this.getCurrentProcessKey());
        final String instanceId = Context.getBpmnExecutionContext().getExecution().getProcessInstanceId();
        updateProcessInstanceStatusUseCase.updateStatus(instanceId, statusKey, processConfig.getStatus(statusKey));

    }

    public void setDescription(final String description) {
        final String instanceId = Context.getBpmnExecutionContext().getExecution().getProcessInstanceId();
        updateProcessInstanceDescriptionUseCase.updateDescription(instanceId, description);
    }

    private String getCurrentProcessKey() {
        return Context.getBpmnExecutionContext().getExecution().getProcessDefinition().getKey();
    }
}
