package io.miranum.integration.s3.repository;

import io.miranum.integration.s3.client.exception.DocumentStorageClientErrorException;
import io.miranum.integration.s3.client.exception.DocumentStorageException;
import io.miranum.integration.s3.client.exception.DocumentStorageServerErrorException;
import io.miranum.integration.s3.client.exception.PropertyNotSetException;
import io.miranum.integration.s3.client.repository.DocumentStorageFileRepository;
import io.miranum.integration.s3.client.repository.presignedurl.PresignedUrlRepository;
import io.miranum.integration.s3.client.repository.transfer.S3FileTransferRepository;
import io.miranum.integration.s3.client.service.ApiClientFactory;
import io.miranum.integration.s3.gen.api.FileApiApi;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class DocumentStorageFileRepositoryTest {

    @Mock
    private ApiClientFactory apiClientFactory;

    @Mock
    private PresignedUrlRepository presignedUrlRepository;

    @Mock
    private S3FileTransferRepository s3FileTransferRepository;

    @Mock
    private FileApiApi fileApi;

    private DocumentStorageFileRepository documentStorageFileRepository;

    @BeforeEach
    public void beforeEach() {
        this.documentStorageFileRepository = new DocumentStorageFileRepository(this.presignedUrlRepository, this.s3FileTransferRepository, this.apiClientFactory);
        Mockito.reset(this.presignedUrlRepository, this.s3FileTransferRepository, this.fileApi, this.apiClientFactory);
    }

    @Test
    void getFile() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        final String pathToFile = "folder/file.txt";
        final int expireInMinutes = 10;
        final String presignedUrl = "the_presignedUrl";

        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.presignedUrlRepository.getPresignedUrlGetFile(pathToFile, expireInMinutes, "url")).thenReturn(Mono.just(presignedUrl));
        Mockito.when(this.s3FileTransferRepository.getFile(presignedUrl)).thenReturn(new byte[]{});
        this.documentStorageFileRepository.getFile(pathToFile, expireInMinutes);

        Mockito.verify(this.presignedUrlRepository, Mockito.times(1)).getPresignedUrlGetFile(pathToFile, expireInMinutes, "url");
        Mockito.verify(this.s3FileTransferRepository, Mockito.times(1)).getFile(presignedUrl);
    }

    @Test
    void saveFile() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        final String pathToFile = "folder/file.txt";
        final byte[] file = new byte[]{1, 2, 3, 4, 5, 6, 7};
        final int expireInMinutes = 10;
        final LocalDate endOfLife = LocalDate.now();
        final String presignedUrl = "the_presignedUrl";


        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.presignedUrlRepository.getPresignedUrlSaveFile(pathToFile, expireInMinutes, endOfLife, "url")).thenReturn(presignedUrl);
        Mockito.doNothing().when(this.s3FileTransferRepository).saveFile(presignedUrl, file);
        this.documentStorageFileRepository.saveFile(pathToFile, file, expireInMinutes, endOfLife);

        Mockito.verify(this.presignedUrlRepository, Mockito.times(1)).getPresignedUrlSaveFile(pathToFile, expireInMinutes, endOfLife, "url");
        Mockito.verify(this.s3FileTransferRepository, Mockito.times(1)).saveFile(presignedUrl, file);
    }

    @Test
    void updateFile() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        final String pathToFile = "folder/file.txt";
        final byte[] file = new byte[]{1, 2, 3, 4, 5, 6, 7};
        final int expireInMinutes = 10;
        final LocalDate endOfLife = LocalDate.now();
        final String presignedUrl = "the_presignedUrl";


        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.presignedUrlRepository.getPresignedUrlUpdateFile(pathToFile, expireInMinutes, endOfLife, "url")).thenReturn(presignedUrl);
        Mockito.doNothing().when(this.s3FileTransferRepository).updateFile(presignedUrl, file);
        this.documentStorageFileRepository.updateFile(pathToFile, file, expireInMinutes, endOfLife);

        Mockito.verify(this.presignedUrlRepository, Mockito.times(1)).getPresignedUrlUpdateFile(pathToFile, expireInMinutes, endOfLife, "url");
        Mockito.verify(this.s3FileTransferRepository, Mockito.times(1)).updateFile(presignedUrl, file);
    }

    @Test
    void updateEndOfLife() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        final String pathToFile = "folder/file.txt";
        final LocalDate endOfLife = LocalDate.now();

        Mockito.reset(this.presignedUrlRepository, this.s3FileTransferRepository, this.fileApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFileApiForDocumentStorageUrl("url")).thenReturn(this.fileApi);
        this.documentStorageFileRepository.updateEndOfLife(pathToFile, endOfLife);
        Mockito.verify(this.fileApi, Mockito.times(1)).updateEndOfLife(pathToFile, endOfLife);

        Mockito.reset(this.presignedUrlRepository, this.s3FileTransferRepository, this.fileApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFileApiForDocumentStorageUrl("url")).thenReturn(this.fileApi);
        Mockito.doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST)).when(this.fileApi).updateEndOfLife(pathToFile, endOfLife);
        Assertions.assertThrows(DocumentStorageClientErrorException.class, () -> this.documentStorageFileRepository.updateEndOfLife(pathToFile, endOfLife));
        Mockito.verify(this.fileApi, Mockito.times(1)).updateEndOfLife(pathToFile, endOfLife);

        Mockito.reset(this.presignedUrlRepository, this.s3FileTransferRepository, this.fileApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFileApiForDocumentStorageUrl("url")).thenReturn(this.fileApi);
        Mockito.doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(this.fileApi).updateEndOfLife(pathToFile, endOfLife);
        Assertions.assertThrows(DocumentStorageServerErrorException.class, () -> this.documentStorageFileRepository.updateEndOfLife(pathToFile, endOfLife));
        Mockito.verify(this.fileApi, Mockito.times(1)).updateEndOfLife(pathToFile, endOfLife);

        Mockito.reset(this.presignedUrlRepository, this.s3FileTransferRepository, this.fileApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFileApiForDocumentStorageUrl("url")).thenReturn(this.fileApi);
        Mockito.doThrow(new RestClientException("Something happened")).when(this.fileApi).updateEndOfLife(pathToFile, endOfLife);
        Assertions.assertThrows(DocumentStorageException.class, () -> this.documentStorageFileRepository.updateEndOfLife(pathToFile, endOfLife));
        Mockito.verify(this.fileApi, Mockito.times(1)).updateEndOfLife(pathToFile, endOfLife);
    }

    @Test
    void deleteFile() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        final String pathToFile = "folder/file.txt";
        final Integer expireInMinutes = 10;
        final String presignedUrl = "the_presignedUrl";

        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.presignedUrlRepository.getPresignedUrlDeleteFile(pathToFile, expireInMinutes, "url")).thenReturn(presignedUrl);
        Mockito.doNothing().when(this.s3FileTransferRepository).deleteFile(presignedUrl);
        this.documentStorageFileRepository.deleteFile(pathToFile, expireInMinutes);

        Mockito.verify(this.presignedUrlRepository, Mockito.times(1)).getPresignedUrlDeleteFile(pathToFile, expireInMinutes, "url");
        Mockito.verify(this.s3FileTransferRepository, Mockito.times(1)).deleteFile(presignedUrl);
    }

}
