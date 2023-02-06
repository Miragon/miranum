package io.miragon.publicplandemocore.application.port.in;

public interface GeneratePDFUseCase {
    byte[] generatePDF(GeneratePDFCommand generatePDFCommand);
}
