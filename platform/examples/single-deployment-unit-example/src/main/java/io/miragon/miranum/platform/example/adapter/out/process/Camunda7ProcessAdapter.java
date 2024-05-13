package io.miragon.miranum.platform.example.adapter.out.process;

import io.miragon.miranum.platform.connect.process.api.ProcessStartingException;
import io.miragon.miranum.platform.connect.process.api.StartProcessCommand;
import io.miragon.miranum.platform.connect.process.impl.StartProcessPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Camunda7ProcessAdapter implements StartProcessPort {

    private final RuntimeService runtimeService;

    @Override
    public void startProcess(final StartProcessCommand startProcessCommand) throws ProcessStartingException {
        this.runtimeService.startProcessInstanceByKey(startProcessCommand.getProcessKey(), startProcessCommand.getVariables());
        log.info("Started new process instance with key {}", startProcessCommand.getProcessKey());
    }
}
