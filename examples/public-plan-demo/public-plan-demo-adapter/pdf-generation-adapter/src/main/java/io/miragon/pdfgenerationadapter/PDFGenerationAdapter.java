package io.miragon.pdfgenerationadapter;

import io.miragon.publicplandemocore.application.port.out.GeneratePDFPort;

public class PDFGenerationAdapter implements GeneratePDFPort {

    @Override
    public byte[] generatePDF(String firstname, String lastname) {
        var content = "Hello" + firstname + " " + lastname;
        return content.getBytes();
    }
}
