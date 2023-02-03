package io.miragon.publicplandemocore.application.service;

import io.miragon.miranum.connect.worker.domain.Worker;
import io.miragon.publicplandemocore.application.port.in.GeneratePDFUseCase;
import lombok.extern.java.Log;

public class GeneratePDFService implements GeneratePDFUseCase {

    @Override
    @Worker(type="generatePDF")
    public void generatePDF() {

    }
}
