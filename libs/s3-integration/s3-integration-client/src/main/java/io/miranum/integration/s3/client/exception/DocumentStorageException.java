package io.miranum.integration.s3.client.exception;

public class DocumentStorageException extends Exception {

    public DocumentStorageException(final String message, final Exception exception) {
        super(message, exception);
    }

}
