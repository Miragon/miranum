package io.miragon.publicplandemocore.application.port.out;

public interface GeneratePDFPort {

    byte[] generatePDF(String firstname, String lastname);
}
