package io.miranum.integration.s3.repository;

import io.miranum.integration.s3.client.exception.DocumentStorageClientErrorException;
import io.miranum.integration.s3.client.exception.DocumentStorageException;
import io.miranum.integration.s3.client.exception.DocumentStorageServerErrorException;
import io.miranum.integration.s3.client.exception.PropertyNotSetException;
import io.miranum.integration.s3.client.repository.DocumentStorageFolderRepository;
import io.miranum.integration.s3.client.service.ApiClientFactory;
import io.miranum.integration.s3.gen.api.FolderApiApi;
import io.miranum.integration.s3.gen.model.FilesInFolderDto;
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

import java.util.Set;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class DocumentStorageFolderRepositoryTest {

    @Mock
    private ApiClientFactory apiClientFactory;

    @Mock
    private FolderApiApi folderApi;

    private DocumentStorageFolderRepository documentStorageFolderRepository;

    @BeforeEach
    public void beforeEach() {
        this.documentStorageFolderRepository = new DocumentStorageFolderRepository(this.apiClientFactory);
    }

    @Test
    void deleteFolder() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        final String pathToFolder = "folder";

        Mockito.reset(this.folderApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFolderApiForDocumentStorageUrl("url")).thenReturn(this.folderApi);
        Mockito.when(this.folderApi.delete(pathToFolder)).thenReturn(Mono.empty());
        this.documentStorageFolderRepository.deleteFolder(pathToFolder);
        Mockito.verify(this.folderApi, Mockito.times(1)).delete(pathToFolder);

        Mockito.reset(this.folderApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFolderApiForDocumentStorageUrl("url")).thenReturn(this.folderApi);
        Mockito.doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST)).when(this.folderApi).delete(pathToFolder);
        Assertions.assertThrows(DocumentStorageClientErrorException.class, () -> this.documentStorageFolderRepository.deleteFolder(pathToFolder));
        Mockito.verify(this.folderApi, Mockito.times(1)).delete(pathToFolder);

        Mockito.reset(this.folderApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFolderApiForDocumentStorageUrl("url")).thenReturn(this.folderApi);
        Mockito.doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(this.folderApi).delete(pathToFolder);
        Assertions.assertThrows(DocumentStorageServerErrorException.class, () -> this.documentStorageFolderRepository.deleteFolder(pathToFolder));
        Mockito.verify(this.folderApi, Mockito.times(1)).delete(pathToFolder);

        Mockito.reset(this.folderApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFolderApiForDocumentStorageUrl("url")).thenReturn(this.folderApi);
        Mockito.doThrow(new RestClientException("Something happened")).when(this.folderApi).delete(pathToFolder);
        Assertions.assertThrows(DocumentStorageException.class, () -> this.documentStorageFolderRepository.deleteFolder(pathToFolder));
        Mockito.verify(this.folderApi, Mockito.times(1)).delete(pathToFolder);
    }

    @Test
    void getAllFilesInFolderRecursively() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        final String pathToFolder = "folder";

        final FilesInFolderDto filesInFolderDto = new FilesInFolderDto();
        filesInFolderDto.setPathToFiles(Set.of("folder/file.txt"));

        Mockito.reset(this.folderApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFolderApiForDocumentStorageUrl("url")).thenReturn(this.folderApi);
        Mockito.when(this.folderApi.getAllFilesInFolderRecursively(pathToFolder)).thenReturn(Mono.just(filesInFolderDto));
        this.documentStorageFolderRepository.getAllFilesInFolderRecursively(pathToFolder);
        Mockito.verify(this.folderApi, Mockito.times(1)).getAllFilesInFolderRecursively(pathToFolder);

        Mockito.reset(this.folderApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFolderApiForDocumentStorageUrl("url")).thenReturn(this.folderApi);
        Mockito.doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST)).when(this.folderApi).getAllFilesInFolderRecursively(pathToFolder);
        Assertions.assertThrows(DocumentStorageClientErrorException.class, () -> this.documentStorageFolderRepository.getAllFilesInFolderRecursively(pathToFolder));
        Mockito.verify(this.folderApi, Mockito.times(1)).getAllFilesInFolderRecursively(pathToFolder);

        Mockito.reset(this.folderApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFolderApiForDocumentStorageUrl("url")).thenReturn(this.folderApi);
        Mockito.doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(this.folderApi).getAllFilesInFolderRecursively(pathToFolder);
        Assertions.assertThrows(DocumentStorageServerErrorException.class, () -> this.documentStorageFolderRepository.getAllFilesInFolderRecursively(pathToFolder));
        Mockito.verify(this.folderApi, Mockito.times(1)).getAllFilesInFolderRecursively(pathToFolder);

        Mockito.reset(this.folderApi, this.apiClientFactory);
        Mockito.when(this.apiClientFactory.getDefaultDocumentStorageUrl()).thenReturn("url");
        Mockito.when(this.apiClientFactory.getFolderApiForDocumentStorageUrl("url")).thenReturn(this.folderApi);
        Mockito.doThrow(new RestClientException("Something happened")).when(this.folderApi).getAllFilesInFolderRecursively(pathToFolder);
        Assertions.assertThrows(DocumentStorageException.class, () -> this.documentStorageFolderRepository.getAllFilesInFolderRecursively(pathToFolder));
        Mockito.verify(this.folderApi, Mockito.times(1)).getAllFilesInFolderRecursively(pathToFolder);
    }

}
