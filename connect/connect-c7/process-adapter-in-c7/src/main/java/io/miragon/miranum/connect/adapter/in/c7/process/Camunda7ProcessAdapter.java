package io.miragon.miranum.connect.adapter.in.c7.process;

import io.miragon.miranum.connect.process.application.port.in.StartProcessCommand;
import io.miragon.miranum.connect.process.application.port.out.StartProcessPort;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.community.rest.client.api.ProcessDefinitionApi;
import org.camunda.community.rest.client.dto.ProcessInstanceWithVariablesDto;
import org.camunda.community.rest.client.dto.StartProcessInstanceDto;
import org.camunda.community.rest.client.dto.VariableValueDto;
import org.camunda.community.rest.client.invoker.ApiException;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class Camunda7ProcessAdapter implements StartProcessPort {

    private final ProcessDefinitionApi processDefinitionApi;

    @Override
    public void startProcess(StartProcessCommand startProcessCommand) {

        // TODO: mapping necessary
        Map<String, VariableValueDto> variables = new HashMap<>();
        variables.put("name", new VariableValueDto().value("name123").type("string"));
        variables.put("key", new VariableValueDto().value("key123").type("string"));

        ProcessInstanceWithVariablesDto processInstance = null;
        try {
            processInstance = this.processDefinitionApi.
                    startProcessInstanceByKey(startProcessCommand.getProcessKey(),
                            new StartProcessInstanceDto().variables(variables));
            log.info("Started process with id {}", processInstance.getId());
        } catch (ApiException e) {
            // temporarily
            throw new RuntimeException(e);
        }
    }
}
