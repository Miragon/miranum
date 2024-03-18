package io.miranum.platform.tasklist.application.service;

import com.google.common.collect.Sets;
import io.holunda.polyflow.view.Task;
import io.holunda.polyflow.view.auth.User;
import io.miranum.integration.s3.client.exception.DocumentStorageClientErrorException;
import io.miranum.integration.s3.client.exception.DocumentStorageException;
import io.miranum.integration.s3.client.exception.DocumentStorageServerErrorException;
import io.miranum.integration.s3.client.exception.PropertyNotSetException;
import io.miranum.integration.s3.client.repository.DocumentStorageFolderRepository;
import io.miranum.platform.tasklist.application.port.out.file.PresignedUrlPort;
import io.miranum.platform.tasklist.application.port.out.file.TaskFileConfigResolverPort;
import io.miranum.platform.tasklist.application.port.out.polyflow.TaskOutPort;
import io.miranum.platform.tasklist.application.port.out.security.CurrentUserPort;
import io.miranum.platform.tasklist.domain.IllegalResourceAccessException;
import io.miranum.platform.tasklist.domain.PresignedUrlAction;
import io.miranum.platform.tasklist.domain.TaskFileConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static io.miranum.platform.tasklist.application.service.TestFixtures.generateTask;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class WorkOnTaskFileUseCaseTest {

    private final DocumentStorageFolderRepository documentStorageFolderRepository = mock(DocumentStorageFolderRepository.class);

    private final PresignedUrlPort presignedUrlPort = mock(PresignedUrlPort.class);

    private final TaskFileConfigResolverPort taskFileConfigResolverPort = mock(TaskFileConfigResolverPort.class);

    private final TaskOutPort taskQueryPort = mock(TaskOutPort.class);

    private final CurrentUserPort currentUserPort = mock(CurrentUserPort.class);

    private final WorkOnTaskFileService useCase = new WorkOnTaskFileService(documentStorageFolderRepository, presignedUrlPort, taskFileConfigResolverPort, taskQueryPort, currentUserPort);

    private final User user = new User("0123456789", Sets.newHashSet("group1", "group2"));

    @BeforeEach
    void setupMocks() {
        Task task = generateTask("task_0", Collections.emptySet(), Collections.emptySet(), user.getUsername(), null, false);
        when(currentUserPort.getCurrentUser()).thenReturn(user);
        when(taskQueryPort.getTaskByIdForCurrentUser(any(), anyString())).thenReturn(task);
    }

    @Test
    void getFileNamesDocumentWithStorageUrl() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        List<String> paths = List.of("able/to/write");
        List<String> pathsReadonly = List.of("able/to/read", "write/also/read");
        when(taskFileConfigResolverPort.apply(any())).thenReturn(new TaskFileConfig("fileContext", "asyncConfig", "syncConfig", paths, pathsReadonly));
        when(documentStorageFolderRepository.getAllFilesInFolderRecursively(anyString())).thenReturn(Mono.just(Sets.newHashSet("able/to/read/file1.txt", "able/to/read/secondfile1.pdf")));
        when(documentStorageFolderRepository.getAllFilesInFolderRecursively(anyString(), anyString())).thenReturn(Mono.just(Sets.newHashSet("able/to/read/file2.txt", "able/to/read/secondfile2.pdf")));

        List<String> listOfNames = useCase.getFileNames("task_0", "able/to/read");

        assertEquals(2, listOfNames.size());
        assertTrue(listOfNames.contains("file2.txt"));
        assertTrue(listOfNames.contains("secondfile2.pdf"));

        verify(documentStorageFolderRepository).getAllFilesInFolderRecursively(anyString(), anyString());
        verifyNoMoreInteractions(documentStorageFolderRepository);

        verify(taskFileConfigResolverPort).apply(any());
        verifyNoMoreInteractions(taskFileConfigResolverPort);
    }

    @Test
    void getFileNamesDocumentWithoutStorageUrl() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        List<String> paths = List.of("able/to/write");
        List<String> pathsReadonly = List.of("able/to/read", "write/also/read");
        when(taskFileConfigResolverPort.apply(any())).thenReturn(new TaskFileConfig("fileContext", "asyncConfig", null, paths, pathsReadonly));
        when(documentStorageFolderRepository.getAllFilesInFolderRecursively(anyString())).thenReturn(Mono.just(Sets.newHashSet("able/to/read/file1.txt", "able/to/read/secondfile1.pdf")));
        when(documentStorageFolderRepository.getAllFilesInFolderRecursively(anyString(), anyString())).thenReturn(Mono.just(Sets.newHashSet("able/to/read/file2.txt", "able/to/read/secondfile2.pdf")));

        List<String> listOfNames = useCase.getFileNames("task_0", "able/to/read");

        assertEquals(2, listOfNames.size());
        assertTrue(listOfNames.contains("file1.txt"));
        assertTrue(listOfNames.contains("secondfile1.pdf"));

        verify(documentStorageFolderRepository).getAllFilesInFolderRecursively(anyString());
        verifyNoMoreInteractions(documentStorageFolderRepository);

        verify(taskFileConfigResolverPort).apply(any());
        verifyNoMoreInteractions(taskFileConfigResolverPort);
    }

    @Test
    void getFileNamesThrowsException() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        List<String> paths = List.of("able/to/write");
        List<String> pathsReadonly = List.of("able/to/read", "write/also/read");
        when(taskFileConfigResolverPort.apply(any())).thenReturn(new TaskFileConfig("fileContext", "asyncConfig", "syncConfig", paths, pathsReadonly));
        when(documentStorageFolderRepository.getAllFilesInFolderRecursively(anyString())).thenThrow(new DocumentStorageException("DocumentStorageException", new Exception()));

        HttpServerErrorException exception = assertThrows(HttpServerErrorException.class, () -> {
            useCase.getFileNames("task_0", "able/to/read");
        });

        String expectedMessage = "Getting all files of folder able/to/read failed";
        String actualMessage = exception.getStatusText();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals(expectedMessage, actualMessage);

        verify(documentStorageFolderRepository).getAllFilesInFolderRecursively(anyString(), anyString());
        verifyNoMoreInteractions(documentStorageFolderRepository);

        verify(taskFileConfigResolverPort).apply(any());
        verifyNoMoreInteractions(taskFileConfigResolverPort);

    }

    @Test
    void getPresignedUrlForGETWithReadAccessAndDocumentStorageUrl() {
        List<String> paths = List.of("able/to/write");
        List<String> pathsReadonly = List.of("able/to/read", "write/also/read");
        when(taskFileConfigResolverPort.apply(any())).thenReturn(new TaskFileConfig("fileContext", "asyncConfig", "syncConfig", paths, pathsReadonly));
        when(presignedUrlPort.getPresignedUrl(anyString(), anyString(), anyInt(), any())).thenReturn("With DocumentStorageUrl");
        when(presignedUrlPort.getPresignedUrl(anyString(), anyInt(), any())).thenReturn("Without DocumentStorageUrl");

        String presignedUrl = useCase.getPresignedUrl(PresignedUrlAction.GET, "task_0", "able/to/read", "filename1.txt");

        assertEquals("With DocumentStorageUrl", presignedUrl);

        verify(presignedUrlPort).getPresignedUrl(anyString(), anyString(), anyInt(), any());
        verifyNoMoreInteractions(presignedUrlPort);

        verify(taskFileConfigResolverPort).apply(any());
        verifyNoMoreInteractions(taskFileConfigResolverPort);

    }

    @Test
    void getPresignedUrlForGETWithWriteAccessAndWithoutDocumentStorageUrl() {
        List<String> paths = List.of("able/to/write");
        List<String> pathsReadonly = List.of("able/to/read", "write/also/read");
        when(taskFileConfigResolverPort.apply(any())).thenReturn(new TaskFileConfig("fileContext", "asyncConfig", null, paths, pathsReadonly));
        when(presignedUrlPort.getPresignedUrl(anyString(), anyString(), anyInt(), any())).thenReturn("With DocumentStorageUrl");
        when(presignedUrlPort.getPresignedUrl(anyString(), anyInt(), any())).thenReturn("Without DocumentStorageUrl");

        String presignedUrl = useCase.getPresignedUrl(PresignedUrlAction.GET, "task_0", "able/to/write", "filename1.txt");

        assertEquals("Without DocumentStorageUrl", presignedUrl);

        verify(presignedUrlPort).getPresignedUrl(anyString(), anyInt(), any());
        verifyNoMoreInteractions(presignedUrlPort);

        verify(taskFileConfigResolverPort).apply(any());
        verifyNoMoreInteractions(taskFileConfigResolverPort);
    }

    @Test
    void getPresignedUrlForPUTWithWriteAccess() {
        List<String> paths = List.of("able/to/write");
        List<String> pathsReadonly = List.of("able/to/read", "write/also/read");
        when(taskFileConfigResolverPort.apply(any())).thenReturn(new TaskFileConfig("fileContext", "asyncConfig", "syncConfig", paths, pathsReadonly));
        when(presignedUrlPort.getPresignedUrl(anyString(), anyString(), anyInt(), any())).thenReturn("With DocumentStorageUrl");

        String presignedUrl = useCase.getPresignedUrl(PresignedUrlAction.PUT, "task_0", "able/to/write", "filename1.txt");

        assertEquals("With DocumentStorageUrl", presignedUrl);

        verify(presignedUrlPort).getPresignedUrl(anyString(), anyString(), anyInt(), any());
        verifyNoMoreInteractions(presignedUrlPort);

        verify(taskFileConfigResolverPort).apply(any());
        verifyNoMoreInteractions(taskFileConfigResolverPort);
    }

    @Test
    void getPresignedUrlForPUTWithReadAccess() {
        List<String> paths = List.of("able/to/write");
        List<String> pathsReadonly = List.of("able/to/read", "write/also/read");
        when(taskFileConfigResolverPort.apply(any())).thenReturn(new TaskFileConfig("fileContext", "asyncConfig", "syncConfig", paths, pathsReadonly));
        when(presignedUrlPort.getPresignedUrl(anyString(), anyString(), anyInt(), any())).thenReturn("With DocumentStorageUrl");

        Exception exception = assertThrows(IllegalResourceAccessException.class, () -> {
            useCase.getPresignedUrl(PresignedUrlAction.PUT, "task_0", "able/to/read", "filename1.txt");
        });

        String expectedMessage = "No access to defined property";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verifyNoInteractions(presignedUrlPort);

        verify(taskFileConfigResolverPort).apply(any());
        verifyNoMoreInteractions(taskFileConfigResolverPort);

    }
}