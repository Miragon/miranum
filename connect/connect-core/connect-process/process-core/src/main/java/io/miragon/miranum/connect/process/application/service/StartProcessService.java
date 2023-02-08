package io.miragon.miranum.connect.process.application.service;

import io.miragon.miranum.connect.process.application.port.in.StartProcessCommand;
import io.miragon.miranum.connect.process.application.port.in.StartProcessUseCase;
import io.miragon.miranum.connect.process.application.port.out.StartProcessPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StartProcessService implements StartProcessUseCase {

    private final StartProcessPort startProcessPort;

    @Override
    public void startProcess(StartProcessCommand command) {
        startProcessPort.startProcess(command);
    }
}
