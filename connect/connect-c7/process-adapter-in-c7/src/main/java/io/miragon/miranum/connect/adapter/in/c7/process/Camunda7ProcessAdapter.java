package io.miragon.miranum.connect.adapter.in.c7.process;

import io.miragon.miranum.connect.c7.utils.Camunda7BaseVariableValueMapper;
import io.miragon.miranum.connect.process.application.port.in.StartProcessCommand;
import io.miragon.miranum.connect.process.application.port.out.StartProcessPort;
import io.miragon.miranum.connect.process.domain.ProcessStartingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.community.rest.client.api.ProcessDefinitionApi;
import org.camunda.community.rest.client.dto.StartProcessInstanceDto;
import org.camunda.community.rest.client.invoker.ApiException;

@RequiredArgsConstructor
@Slf4j
public class Camunda7ProcessAdapter implements StartProcessPort {

    private final ProcessDefinitionApi processDefinitionApi;
    private final Camunda7BaseVariableValueMapper baseVariableMapper;

    @Override
    public void startProcess(StartProcessCommand startProcessCommand) throws ProcessStartingException {
        var variables = baseVariableMapper.map(startProcessCommand.getVariables());

        try {
            var processInstance = this.processDefinitionApi.startProcessInstanceByKey(
                    startProcessCommand.getProcessKey(),
                    new StartProcessInstanceDto().variables(variables));
            log.info("Started new process instance with id {}", processInstance.getId());
        } catch (ApiException e) {
            throw new ProcessStartingException("Failed to create new process instance.", e);
        }
    }
}