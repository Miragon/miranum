package io.miragon.miranum.connect.process.impl;

import io.miragon.miranum.connect.process.api.ProcessApi;
import io.miragon.miranum.connect.process.api.StartProcessCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProcessApiImpl implements ProcessApi {

    private final StartProcessPort startProcessPort;

    @Override
    public void startProcess(final StartProcessCommand command) {
        this.startProcessPort.startProcess(command);
    }
}
