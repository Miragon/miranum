package io.miranum.integration.s3.client.exception;

public class DocumentStorageServerErrorException extends Exception {

    public DocumentStorageServerErrorException(final String message, final Exception exception) {
        super(message, exception);
    }

}
