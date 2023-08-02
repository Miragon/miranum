package io.miranum.platform.tasklist.adapter.out.file;


import io.miranum.integration.s3.client.exception.DocumentStorageClientErrorException;
import io.miranum.integration.s3.client.exception.DocumentStorageException;
import io.miranum.integration.s3.client.exception.DocumentStorageServerErrorException;
import io.miranum.integration.s3.client.repository.presignedurl.PresignedUrlRepository;
import io.miranum.platform.tasklist.application.port.out.file.PresignedUrlPort;
import io.miranum.platform.tasklist.domain.PresignedUrlAction;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PresignedUrlAdapterTest {

    private final PresignedUrlRepository presignedUrlRepository = mock(PresignedUrlRepository.class);

    private final S3Properties s3Properties = new S3Properties();

    private final PresignedUrlPort presignedUrlAdapter = new PresignedUrlAdapter(presignedUrlRepository, s3Properties);


    @Test
    void getPresignedUrlWithGET() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {

        when(presignedUrlRepository.getPresignedUrlGetFile(anyString(), anyInt(), anyString())).thenReturn(Mono.just("Presigned URL for download"));
        String presignedUrl = presignedUrlAdapter.getPresignedUrl("storageURL", "path", 1, PresignedUrlAction.GET);

        assertEquals("Presigned URL for download", presignedUrl);
        verify(presignedUrlRepository).getPresignedUrlGetFile("path", 1, "storageURL");
        verifyNoMoreInteractions(presignedUrlRepository);

    }

    @Test
    void getPresignedUrlWithPOST() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {

        when(presignedUrlRepository.getPresignedUrlSaveFile(anyString(), anyInt(), any(), anyString())).thenReturn("Presigned URL for upload");
        String presignedUrl = presignedUrlAdapter.getPresignedUrl("storageURL", "path", 1, PresignedUrlAction.POST);

        assertEquals("Presigned URL for upload", presignedUrl);
        verify(presignedUrlRepository).getPresignedUrlSaveFile("path", 1, null, "storageURL");
        verifyNoMoreInteractions(presignedUrlRepository);

    }

    @Test
    void getPresignedUrlWithDELETE() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {

        when(presignedUrlRepository.getPresignedUrlDeleteFile(anyString(), anyInt(), anyString())).thenReturn("Presigned URL for delete");
        String presignedUrl = presignedUrlAdapter.getPresignedUrl("storageURL", "path", 1, PresignedUrlAction.DELETE);

        assertEquals("Presigned URL for delete", presignedUrl);
        verify(presignedUrlRepository).getPresignedUrlDeleteFile("path", 1, "storageURL");
        verifyNoMoreInteractions(presignedUrlRepository);

    }

    @Test
    void getPresignedUrlWithGETThrowsException() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {

        when(presignedUrlRepository.getPresignedUrlGetFile(anyString(), anyInt(), anyString())).thenThrow(new DocumentStorageException("DocumentStorageException", new Exception()));
        HttpServerErrorException exception = assertThrows(HttpServerErrorException.class, () -> {
            presignedUrlAdapter.getPresignedUrl("storageURL", "path", 1, PresignedUrlAction.GET);
        });

        String expectedMessage = "Getting presigned url for downloading file path failed";
        String actualMessage = exception.getStatusText();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        verify(presignedUrlRepository).getPresignedUrlGetFile("path", 1, "storageURL");
        verifyNoMoreInteractions(presignedUrlRepository);

    }

    @Test
    void getPresignedUrlWithPOSTThrowsException() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {

        when(presignedUrlRepository.getPresignedUrlSaveFile(anyString(), anyInt(), any(), anyString())).thenThrow(new DocumentStorageClientErrorException("DocumentStorageClientErrorException", new Exception()));
        HttpServerErrorException exception = assertThrows(HttpServerErrorException.class, () -> {
            presignedUrlAdapter.getPresignedUrl("storageURL", "path", 1, PresignedUrlAction.POST);
        });

        String expectedMessage = "Getting presigned url for uploading file path failed";
        String actualMessage = exception.getStatusText();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        verify(presignedUrlRepository).getPresignedUrlSaveFile("path", 1, null, "storageURL");
        verifyNoMoreInteractions(presignedUrlRepository);

    }

    @Test
    void getPresignedUrlWithDELETEThrowsException() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {

        when(presignedUrlRepository.getPresignedUrlDeleteFile(anyString(), anyInt(), anyString())).thenThrow(new DocumentStorageServerErrorException("DocumentStorageServerErrorException", new Exception()));
        HttpServerErrorException exception = assertThrows(HttpServerErrorException.class, () -> {
            presignedUrlAdapter.getPresignedUrl("storageURL", "path", 1, PresignedUrlAction.DELETE);
        });

        String expectedMessage = "Getting presigned url for deleting file path failed";
        String actualMessage = exception.getStatusText();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        verify(presignedUrlRepository).getPresignedUrlDeleteFile("path", 1, "storageURL");
        verifyNoMoreInteractions(presignedUrlRepository);

    }

    @Test
    void getPresignedUrlWithPOSTThrowsConflictingResourceException() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {

        when(presignedUrlRepository.getPresignedUrlSaveFile(anyString(), anyInt(), any(), anyString())).thenThrow(new DocumentStorageException("DocumentStorageException " + HttpStatus.CONFLICT.toString(), new Exception()));
        Exception exception = assertThrows(ConflictingResourceException.class, () -> {
            presignedUrlAdapter.getPresignedUrl("storageURL", "path", 1, PresignedUrlAction.POST);
        });

        String expectedMessage = "Getting presigned url for uploading file path failed";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
        verify(presignedUrlRepository).getPresignedUrlSaveFile("path", 1, null, "storageURL");
        verifyNoMoreInteractions(presignedUrlRepository);

    }

    @Test
    void getPresignedUrlWithGETThrowsNoConflictingResourceException() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {

        when(presignedUrlRepository.getPresignedUrlGetFile(anyString(), anyInt(), anyString())).thenThrow(new DocumentStorageException("DocumentStorageException " + HttpStatus.CONFLICT.toString(), new Exception()));
        HttpServerErrorException exception = assertThrows(HttpServerErrorException.class, () -> {
            presignedUrlAdapter.getPresignedUrl("storageURL", "path", 1, PresignedUrlAction.GET);
        });

        String expectedMessage = "Getting presigned url for downloading file path failed";
        String actualMessage = exception.getStatusText();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        verify(presignedUrlRepository).getPresignedUrlGetFile("path", 1, "storageURL");
        verifyNoMoreInteractions(presignedUrlRepository);

    }
}
