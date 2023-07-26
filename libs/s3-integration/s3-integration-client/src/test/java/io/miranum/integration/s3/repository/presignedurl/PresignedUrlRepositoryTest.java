package io.miranum.integration.s3.repository.presignedurl;

import io.miranum.integration.s3.client.exception.DocumentStorageClientErrorException;
import io.miranum.integration.s3.client.exception.DocumentStorageException;
import io.miranum.integration.s3.client.exception.DocumentStorageServerErrorException;
import io.miranum.integration.s3.client.exception.PropertyNotSetException;
import io.miranum.integration.s3.client.repository.presignedurl.PresignedUrlRepository;
import io.miranum.integration.s3.client.service.ApiClientFactory;
import io.miranum.integration.s3.gen.api.FileApiApi;
import io.miranum.integration.s3.gen.model.FileDataDto;
import io.miranum.integration.s3.gen.model.PresignedUrlDto;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class PresignedUrlRepositoryTest {

    @Mock
    private ApiClientFactory apiClientFactory;

    @Mock
    private FileApiApi fileApi;

    private PresignedUrlRepository presignedUrlRepository;

    @BeforeEach
    public void beforeEach() {
        this.presignedUrlRepository = new PresignedUrlRepository(this.apiClientFactory);
        Mockito.reset(this.fileApi, this.apiClientFactory);
    }

    @Test
    void getPresignedUrlGetFile() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        final String pathToFile = "folder/file.txt";
        final Integer expireInMinutes = 10;

        final PresignedUrlDto expected = new PresignedUrlDto();
        expected.setUrl("the_presignedUrl");

        Mockito.reset(this.fileApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFileApiForDocumentStorageUrl("url")).thenReturn(this.fileApi);
        Mockito.when(this.fileApi.get(pathToFile, expireInMinutes)).thenReturn(Mono.just(expected));
        final Mono<String> result = this.presignedUrlRepository.getPresignedUrlGetFile(pathToFile, expireInMinutes);
        Mockito.verify(this.fileApi, Mockito.times(1)).get(pathToFile, expireInMinutes);
        assertThat(result.block(), is(expected.getUrl()));
        Mockito.reset(this.fileApi);
    }

    @Test
    void getPresignedUrlGetFileException() throws PropertyNotSetException {
        final String pathToFile = "folder/file.txt";
        final Integer expireInMinutes = 10;

        Mockito.reset(this.fileApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFileApiForDocumentStorageUrl("url")).thenReturn(this.fileApi);
        Mockito.when(this.fileApi.get(pathToFile, expireInMinutes)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
        Assertions.assertThrows(DocumentStorageClientErrorException.class, () -> this.presignedUrlRepository.getPresignedUrlGetFile(pathToFile, expireInMinutes));
        Mockito.verify(this.fileApi, Mockito.times(1)).get(pathToFile, expireInMinutes);

        Mockito.reset(this.fileApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFileApiForDocumentStorageUrl("url")).thenReturn(this.fileApi);
        Mockito.when(this.fileApi.get(pathToFile, expireInMinutes)).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        Assertions.assertThrows(DocumentStorageServerErrorException.class, () -> this.presignedUrlRepository.getPresignedUrlGetFile(pathToFile, expireInMinutes));
        Mockito.verify(this.fileApi, Mockito.times(1)).get(pathToFile, expireInMinutes);

        Mockito.reset(this.fileApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFileApiForDocumentStorageUrl("url")).thenReturn(this.fileApi);
        Mockito.when(this.fileApi.get(pathToFile, expireInMinutes)).thenThrow(new RestClientException("Something happened"));
        Assertions.assertThrows(DocumentStorageException.class, () -> this.presignedUrlRepository.getPresignedUrlGetFile(pathToFile, expireInMinutes));
        Mockito.verify(this.fileApi, Mockito.times(1)).get(pathToFile, expireInMinutes);
    }

    @Test
    void getPresignedUrlSaveFile() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        final String pathToFile = "folder/file.txt";
        final Integer expireInMinutes = 10;
        final LocalDate endOfLife = LocalDate.now();

        final FileDataDto fileDataDto = new FileDataDto();
        fileDataDto.setPathToFile(pathToFile);
        fileDataDto.setExpiresInMinutes(expireInMinutes);
        fileDataDto.setEndOfLife(endOfLife);

        final PresignedUrlDto expected = new PresignedUrlDto();
        expected.setUrl("the_presignedUrl");

        Mockito.reset(this.fileApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFileApiForDocumentStorageUrl("url")).thenReturn(this.fileApi);
        Mockito.when(this.fileApi.save(fileDataDto)).thenReturn(Mono.just(expected));

        final String result = this.presignedUrlRepository.getPresignedUrlSaveFile(pathToFile, expireInMinutes, endOfLife);
        Mockito.verify(this.fileApi, Mockito.times(1)).save(fileDataDto);
        assertThat(result, is(expected.getUrl()));
    }

    @Test
    void getPresignedUrlSaveFileException() throws PropertyNotSetException {
        final String pathToFile = "folder/file.txt";
        final Integer expireInMinutes = 10;
        final LocalDate endOfLife = LocalDate.now();

        final FileDataDto fileDataDto = new FileDataDto();
        fileDataDto.setPathToFile(pathToFile);
        fileDataDto.setExpiresInMinutes(expireInMinutes);
        fileDataDto.setEndOfLife(endOfLife);

        Mockito.reset(this.fileApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFileApiForDocumentStorageUrl("url")).thenReturn(this.fileApi);
        Mockito.when(this.fileApi.save(fileDataDto)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
        Assertions.assertThrows(DocumentStorageClientErrorException.class, () -> this.presignedUrlRepository.getPresignedUrlSaveFile(pathToFile, expireInMinutes, endOfLife));
        Mockito.verify(this.fileApi, Mockito.times(1)).save(fileDataDto);

        Mockito.reset(this.fileApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFileApiForDocumentStorageUrl("url")).thenReturn(this.fileApi);
        Mockito.when(this.fileApi.save(fileDataDto)).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        Assertions.assertThrows(DocumentStorageServerErrorException.class, () -> this.presignedUrlRepository.getPresignedUrlSaveFile(pathToFile, expireInMinutes, endOfLife));
        Mockito.verify(this.fileApi, Mockito.times(1)).save(fileDataDto);

        Mockito.reset(this.fileApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFileApiForDocumentStorageUrl("url")).thenReturn(this.fileApi);
        Mockito.when(this.fileApi.save(fileDataDto)).thenThrow(new RestClientException("Something happened"));
        Assertions.assertThrows(DocumentStorageException.class, () -> this.presignedUrlRepository.getPresignedUrlSaveFile(pathToFile, expireInMinutes, endOfLife));
        Mockito.verify(this.fileApi, Mockito.times(1)).save(fileDataDto);
    }

    @Test
    void getPresignedUrlUpdateFile() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        final String pathToFile = "folder/file.txt";
        final Integer expireInMinutes = 10;
        final LocalDate endOfLife = LocalDate.now();

        final FileDataDto fileDataDto = new FileDataDto();
        fileDataDto.setPathToFile(pathToFile);
        fileDataDto.setExpiresInMinutes(expireInMinutes);
        fileDataDto.setEndOfLife(endOfLife);

        final PresignedUrlDto expected = new PresignedUrlDto();
        expected.setUrl("the_presignedUrl");

        Mockito.reset(this.fileApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFileApiForDocumentStorageUrl("url")).thenReturn(this.fileApi);
        Mockito.when(this.fileApi.update(fileDataDto)).thenReturn(Mono.just(expected));

        final String result = this.presignedUrlRepository.getPresignedUrlUpdateFile(pathToFile, expireInMinutes, endOfLife);
        Mockito.verify(this.fileApi, Mockito.times(1)).update(fileDataDto);
        assertThat(result, is(expected.getUrl()));
    }

    @Test
    void getPresignedUrlUpdateFileException() throws PropertyNotSetException {
        final String pathToFile = "folder/file.txt";
        final Integer expireInMinutes = 10;
        final LocalDate endOfLife = LocalDate.now();

        final FileDataDto fileDataDto = new FileDataDto();
        fileDataDto.setPathToFile(pathToFile);
        fileDataDto.setExpiresInMinutes(expireInMinutes);
        fileDataDto.setEndOfLife(endOfLife);

        Mockito.reset(this.fileApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFileApiForDocumentStorageUrl("url")).thenReturn(this.fileApi);
        Mockito.when(this.fileApi.update(fileDataDto)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
        Assertions.assertThrows(DocumentStorageClientErrorException.class, () -> this.presignedUrlRepository.getPresignedUrlUpdateFile(pathToFile, expireInMinutes, endOfLife));
        Mockito.verify(this.fileApi, Mockito.times(1)).update(fileDataDto);

        Mockito.reset(this.fileApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFileApiForDocumentStorageUrl("url")).thenReturn(this.fileApi);
        Mockito.when(this.fileApi.update(fileDataDto)).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        Assertions.assertThrows(DocumentStorageServerErrorException.class, () -> this.presignedUrlRepository.getPresignedUrlUpdateFile(pathToFile, expireInMinutes, endOfLife));
        Mockito.verify(this.fileApi, Mockito.times(1)).update(fileDataDto);

        Mockito.reset(this.fileApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFileApiForDocumentStorageUrl("url")).thenReturn(this.fileApi);
        Mockito.when(this.fileApi.update(fileDataDto)).thenThrow(new RestClientException("Something happened"));
        Assertions.assertThrows(DocumentStorageException.class, () -> this.presignedUrlRepository.getPresignedUrlUpdateFile(pathToFile, expireInMinutes, endOfLife));
        Mockito.verify(this.fileApi, Mockito.times(1)).update(fileDataDto);
    }

    @Test
    void getPresignedUrlDeleteFile() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        final String pathToFile = "folder/file.txt";
        final Integer expireInMinutes = 10;

        final PresignedUrlDto expected = new PresignedUrlDto();
        expected.setUrl("the_presignedUrl");

        Mockito.reset(this.fileApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFileApiForDocumentStorageUrl("url")).thenReturn(this.fileApi);
        Mockito.when(this.fileApi.delete1(pathToFile, expireInMinutes)).thenReturn(Mono.just(expected));

        final String result = this.presignedUrlRepository.getPresignedUrlDeleteFile(pathToFile, expireInMinutes);
        Mockito.verify(this.fileApi, Mockito.times(1)).delete1(pathToFile, expireInMinutes);
        assertThat(result, is(expected.getUrl()));
    }

    @Test
    void getPresignedUrlDeleteFileException() throws PropertyNotSetException {
        final String pathToFile = "folder/file.txt";
        final Integer expireInMinutes = 10;

        Mockito.reset(this.fileApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFileApiForDocumentStorageUrl("url")).thenReturn(this.fileApi);
        Mockito.when(this.fileApi.delete1(pathToFile, expireInMinutes)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
        Assertions.assertThrows(DocumentStorageClientErrorException.class, () -> this.presignedUrlRepository.getPresignedUrlDeleteFile(pathToFile, expireInMinutes));
        Mockito.verify(this.fileApi, Mockito.times(1)).delete1(pathToFile, expireInMinutes);

        Mockito.reset(this.fileApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFileApiForDocumentStorageUrl("url")).thenReturn(this.fileApi);
        Mockito.when(this.fileApi.delete1(pathToFile, expireInMinutes)).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        Assertions.assertThrows(DocumentStorageServerErrorException.class, () -> this.presignedUrlRepository.getPresignedUrlDeleteFile(pathToFile, expireInMinutes));
        Mockito.verify(this.fileApi, Mockito.times(1)).delete1(pathToFile, expireInMinutes);

        Mockito.reset(this.fileApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFileApiForDocumentStorageUrl("url")).thenReturn(this.fileApi);
        Mockito.when(this.fileApi.delete1(pathToFile, expireInMinutes)).thenThrow(new RestClientException("Something happened"));
        Assertions.assertThrows(DocumentStorageException.class, () -> this.presignedUrlRepository.getPresignedUrlDeleteFile(pathToFile, expireInMinutes));
        Mockito.verify(this.fileApi, Mockito.times(1)).delete1(pathToFile, expireInMinutes);
    }

}
