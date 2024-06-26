package io.miragon.miranum.connect.camunda7.remote;

import io.miragon.miranum.connect.camnda7.remote.utils.Camunda7RestValueMapper;
import io.miragon.miranum.connect.process.api.ProcessStartingException;
import io.miragon.miranum.connect.process.api.StartProcessCommand;
import io.miragon.miranum.connect.process.impl.StartProcessPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.community.rest.client.api.ProcessDefinitionApi;
import org.camunda.community.rest.client.dto.StartProcessInstanceDto;

@RequiredArgsConstructor
@Slf4j
public class Camunda7ProcessAdapter implements StartProcessPort {

    private final ProcessDefinitionApi processDefinitionApi;
    private final Camunda7RestValueMapper baseVariableMapper;

    @Override
    public void startProcess(final StartProcessCommand startProcessCommand) throws ProcessStartingException {

        try {
            final var variables = this.baseVariableMapper.map(startProcessCommand.getVariables());
            final var processInstance = this.processDefinitionApi.startProcessInstanceByKey(
                    startProcessCommand.getProcessKey(),
                    new StartProcessInstanceDto()
                            .variables(variables)
                            .businessKey(startProcessCommand.getCorrelationKey()));
            log.debug("Started new process instance with id {}", processInstance.getId());
        } catch (final Exception e) {
            throw new ProcessStartingException("Failed to create new process instance.", e);
        }
    }
}
