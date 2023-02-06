package io.miragon.publicplandemocore.application.service;

import io.miragon.miranum.connect.worker.domain.Worker;
import io.miragon.publicplandemocore.application.port.in.StorePDFCommand;
import io.miragon.publicplandemocore.application.port.in.StorePDFUseCase;
import io.miragon.publicplandemocore.application.port.out.StorePDFPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StorePDFService implements StorePDFUseCase {

    private final StorePDFPort storePDFPort;

    @Override
    @Worker(type="storePDF")
    public void storePDF(StorePDFCommand storePDFCommand) {
        storePDFPort.storePDF(storePDFCommand.getBytes());
    }
}
