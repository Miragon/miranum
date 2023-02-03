package io.miragon.publicplandemocore.application.service;

import io.miragon.miranum.connect.worker.domain.Worker;
import io.miragon.publicplandemocore.application.port.in.StorePDFUseCase;

public class StorePDFService implements StorePDFUseCase {
    @Override
    @Worker(type="storePDF")
    public void storePDF() {

    }
}
