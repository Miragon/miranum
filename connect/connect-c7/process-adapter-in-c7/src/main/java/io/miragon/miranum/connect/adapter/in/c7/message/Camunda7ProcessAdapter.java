package io.miragon.miranum.connect.adapter.in.c7.message;

import io.miragon.miranum.connect.process.application.port.in.StartProcessCommand;
import io.miragon.miranum.connect.process.application.port.out.StartProcessPort;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;

@RequiredArgsConstructor
public class Camunda7ProcessAdapter implements StartProcessPort {

    @Override
    public void startProcess(StartProcessCommand startProcessCommand) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        runtimeService.startProcessInstanceByKey(
                startProcessCommand.getProcessKey(),
                "default",
                startProcessCommand.getVariables());
    }
}
