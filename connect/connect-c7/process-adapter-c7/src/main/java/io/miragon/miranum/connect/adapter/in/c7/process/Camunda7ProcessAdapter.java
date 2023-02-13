package io.miragon.miranum.connect.adapter.in.c7.process;

import io.miragon.miranum.connect.c7.utils.Camunda7BaseVariableValueMapper;
import io.miragon.miranum.connect.process.api.ProcessStartingException;
import io.miragon.miranum.connect.process.api.StartProcessCommand;
import io.miragon.miranum.connect.process.impl.StartProcessPort;
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
    public void startProcess(final StartProcessCommand startProcessCommand) throws ProcessStartingException {
        final var variables = this.baseVariableMapper.map(startProcessCommand.getVariables());

        try {
            final var processInstance = this.processDefinitionApi.startProcessInstanceByKey(
                    startProcessCommand.getProcessKey(),
                    new StartProcessInstanceDto().variables(variables));
            log.info("Started new process instance with id {}", processInstance.getId());
        } catch (final ApiException e) {
            throw new ProcessStartingException("Failed to create new process instance.", e);
        }
    }
}
