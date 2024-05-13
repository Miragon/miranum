package io.miragon.miranum.platform.connect.process.impl;

import io.miragon.miranum.platform.connect.process.api.StartProcessCommand;
import io.miragon.miranum.platform.connect.process.api.ProcessApi;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProcessApiImpl implements ProcessApi {

    private final StartProcessPort startProcessPort;

    @Override
    public void startProcess(final StartProcessCommand command) {
        this.startProcessPort.startProcess(command);
    }
}
