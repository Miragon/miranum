package io.miranum.platform.connect.process.impl;

import io.miranum.platform.connect.process.api.ProcessApi;
import io.miranum.platform.connect.process.api.StartProcessCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProcessApiImpl implements ProcessApi {

    private final StartProcessPort startProcessPort;

    @Override
    public void startProcess(final StartProcessCommand command) {
        this.startProcessPort.startProcess(command);
    }
}
