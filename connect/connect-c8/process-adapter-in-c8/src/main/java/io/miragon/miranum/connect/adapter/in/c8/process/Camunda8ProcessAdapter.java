package io.miragon.miranum.connect.adapter.in.c8.process;

import io.camunda.zeebe.client.ZeebeClient;
import io.miragon.miranum.connect.process.application.port.in.StartProcessCommand;
import io.miragon.miranum.connect.process.application.port.out.StartProcessPort;
import io.miragon.miranum.connect.process.domain.ProcessStartingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class Camunda8ProcessAdapter implements StartProcessPort {

    private final ZeebeClient zeebeClient;

    @Override
    public void startProcess(StartProcessCommand startProcessCommand) throws ProcessStartingException {
        zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId(startProcessCommand.getProcessKey()).latestVersion()
                .variables(startProcessCommand.getVariables())
                .send()
                .whenComplete((result, exception) -> {
                    if (Objects.isNull(exception)) {
                        log.info("Started new process instance with id {}", result.getProcessInstanceKey());
                    } else {
                        throw new ProcessStartingException("Failed to create new process instance.", (Exception) exception);
                    }
                });
    }
}