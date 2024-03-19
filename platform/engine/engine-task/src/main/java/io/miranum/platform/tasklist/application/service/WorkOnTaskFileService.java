package io.miranum.platform.tasklist.application.service;

import io.miranum.integration.s3.client.repository.DocumentStorageFolderRepository;
import io.miranum.platform.tasklist.application.port.out.file.PresignedUrlPort;
import io.miranum.platform.tasklist.application.port.out.file.TaskFileConfigResolverPort;
import io.miranum.platform.tasklist.application.port.out.polyflow.TaskOutPort;
import io.miranum.platform.tasklist.application.port.out.security.CurrentUserPort;
import io.miranum.platform.tasklist.domain.PresignedUrlAction;
import io.miranum.platform.tasklist.domain.Task;
import io.miranum.platform.tasklist.domain.TaskFileConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class WorkOnTaskFileService implements io.miranum.platform.tasklist.application.port.in.WorkOnTaskFileUseCase {

    protected final DocumentStorageFolderRepository documentStorageFolderRepository;

    private final PresignedUrlPort presignedUrlPort;

    private final TaskFileConfigResolverPort taskFileConfigResolverPort;

    private final TaskOutPort taskQueryPort;

    private final CurrentUserPort currentUserPort;

    private TaskFileConfig fileConfig;


    public List<String> getFileNames(final String taskId, final String filePath) {

        this.initializeFileConfig(taskId);
        this.fileConfig.checkReadAccess(filePath);

        final String fileContext = this.fileConfig.processFileContext;

        try {
            String documentStorageUrl = this.fileConfig.processSyncConfig;
            if (documentStorageUrl != null) {
                return this.removeFolderFromPaths(this.documentStorageFolderRepository.getAllFilesInFolderRecursively(fileContext + "/" + filePath, documentStorageUrl).block());
            }
            return this.removeFolderFromPaths(this.documentStorageFolderRepository.getAllFilesInFolderRecursively(fileContext + "/" + filePath).block());
        } catch (final Exception ex) {
            log.error("Getting all files of folder {} failed: {}", filePath, ex);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Getting all files of folder %s failed", filePath));
        }
    }

    public String getPresignedUrl(final PresignedUrlAction action, final String taskId, final String filePath, final String fileName) {

        this.initializeFileConfig(taskId);

        if (action.equals(PresignedUrlAction.GET)) {
            this.fileConfig.checkReadAccess(filePath);
        } else {
            this.fileConfig.checkWriteAccess(filePath);
        }

        final String fileContext = this.fileConfig.processFileContext;

        String documentStorageUrl = this.fileConfig.processSyncConfig;
        String pathToFile = fileContext + "/" + filePath + "/" + fileName;

        if (documentStorageUrl != null) {
            return presignedUrlPort.getPresignedUrl(documentStorageUrl, pathToFile, 5, action);
        }
        return presignedUrlPort.getPresignedUrl(pathToFile, 5, action);
    }

    private List<String> removeFolderFromPaths(final Set<String> fileList) {
        return fileList.stream()
                .map(file -> file.substring(file.lastIndexOf("/") + 1))
                .collect(Collectors.toList());
    }

    private void initializeFileConfig(String taskId) {
        Task task = getTaskForUser(taskId);
        this.fileConfig = taskFileConfigResolverPort.apply(task);
    }

    private Task getTaskForUser(String taskId) {
        val currentUser = currentUserPort.getCurrentUser();
        return taskQueryPort.getTaskByIdForCurrentUser(currentUser, taskId);
    }


}
