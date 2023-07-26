package io.miranum.integration.s3.client.exception;

public class DocumentStorageClientErrorException extends Exception {

    public DocumentStorageClientErrorException(final String message, final Exception exception) {
        super(message, exception);
    }

}
