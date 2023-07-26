/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2022
 */
package io.muenchendigital.digiwf.s3.integration.configuration.nfcconverter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;


/**
 * <p>Wrapper für Reader der eine NFC-Konvertierung durchführt.</p>
 *
 * <p><strong>Achtung:</strong><ul>
 * <li>Bei Java-Readern und -Writern kann gefahrlos eine NFC-Konvertierung
 * durchgeführt werden, da dort Zeichen verarbeitet werden.</li>
 * <li>Dieser Reader liest bei vor dem Lesen des ersten Zeichens denn vollständig Text des
 * gewrappten Readers in einern internen Buffer und führt darauf die NFC-Normalisierung
 * durch. Grund ist, dass NFC-Konvertierung kann nicht auf Basis von einzelnen Zeichen
 * durchgeführt werden kann. Dies kann zu erhöhter Latenz führen.</li>
 * </ul></p>
 */
@Slf4j
public class NfcReader extends Reader {

    private final Reader original;

    private CharArrayReader converted;

    public NfcReader(final Reader original) {
        this.original = original;
        this.converted = null;
    }

    private void convert() {

        if (converted != null) {
            return;
        }

        log.debug("Converting Reader data to NFC.");
        try {
            final String nfdContent = IOUtils.toString(original);
            final String nfcConvertedContent = NfcHelper.nfcConverter(nfdContent);
            converted = new CharArrayReader(nfcConvertedContent.toCharArray());

        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int read() throws IOException {
        convert();
        return converted.read();
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        convert();
        return converted.read(cbuf, off, len);
    }

    @Override
    public void close() throws IOException {
        // Nothing to do
    }

    @Override
    public long skip(long n) throws IOException {
        convert();
        return converted.skip(n);
    }

    @Override
    public boolean ready() throws IOException {
        convert();
        return converted.ready();
    }

    @Override
    public boolean markSupported() {
        convert();
        return converted.markSupported();
    }

    @Override
    public void mark(int readAheadLimit) throws IOException {
        convert();
        converted.mark(readAheadLimit);
    }

    @Override
    public void reset() throws IOException {
        convert();
        converted.reset();
    }

}
