package io.miragon.miranum.connect.adapter.in.c8.process;

import io.camunda.zeebe.client.ZeebeClient;
import io.miragon.miranum.connect.process.api.ProcessStartingException;
import io.miragon.miranum.connect.process.api.StartProcessCommand;
import io.miragon.miranum.connect.process.impl.StartProcessPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class Camunda8ProcessAdapter implements StartProcessPort {

    private final ZeebeClient zeebeClient;

    @Override
    public void startProcess(final StartProcessCommand startProcessCommand) throws ProcessStartingException {
        this.zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId(startProcessCommand.getProcessKey()).latestVersion()
                .variables(startProcessCommand.getVariables())
                .send()
                .whenComplete((result, exception) -> {
                    if (Objects.isNull(exception)) {
                        log.debug("Started new process instance with id {}", result.getProcessInstanceKey());
                    } else {
                        throw new ProcessStartingException("Failed to create new process instance.", (Exception) exception);
                    }
                });
    }
}
