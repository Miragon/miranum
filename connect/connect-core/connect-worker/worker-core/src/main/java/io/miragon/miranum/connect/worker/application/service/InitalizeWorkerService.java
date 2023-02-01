package io.miragon.miranum.connect.worker.application.service;

import io.miragon.miranum.connect.worker.application.port.in.InitalizeWorkerCommand;
import io.miragon.miranum.connect.worker.application.port.in.InitializeUseCase;
import io.miragon.miranum.connect.worker.application.port.out.BindWorkerPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InitalizeWorkerService implements InitializeUseCase {

    private final BindWorkerPort bindWorkerPort;

    @Override
    public void initalize(final InitalizeWorkerCommand initalizeWorkerCommand) {
        initalizeWorkerCommand.getWorkerList().forEach(this.bindWorkerPort::bind);
    }
}
