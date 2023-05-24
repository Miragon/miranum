package io.miranum.platform.engine.adapter.out.engine;

import io.miranum.platform.engine.application.port.out.engine.ServiceInstanceVariablePort;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ServiceInstanceVariableAdapter implements ServiceInstanceVariablePort {

    private final HistoryService historyService;

    @Override
    public Map<String, Object> getVariables(String instanceId) {
        return this.historyService.createHistoricVariableInstanceQuery()
                .executionIdIn(instanceId)
                .processInstanceId(instanceId)
                .list()
                .stream()
                .filter(obj -> obj.getValue() != null)
                .collect(Collectors.toMap(HistoricVariableInstance::getName, HistoricVariableInstance::getValue));
    }
}
