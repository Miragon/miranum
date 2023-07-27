/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.miranum.platform.engine.application.service;

import io.miranum.integration.s3.client.repository.DocumentStorageFolderRepository;
import io.miranum.platform.engine.adapter.in.engine.ProcessConfigFunctions;
import io.miranum.platform.engine.application.port.in.process.GetFileNamesForProcessStartUseCase;
import io.miranum.platform.engine.application.port.in.process.GetPresignedUrlForProcessStartUseCase;
import io.miranum.platform.engine.application.port.out.file.PresignedUrlAdapter;
import io.miranum.platform.engine.application.port.out.process.ProcessConfigPort;
import io.miranum.platform.engine.application.port.out.process.StartContextPort;
import io.miranum.platform.engine.domain.file.IllegalResourceAccessException;
import io.miranum.platform.engine.domain.file.PresignedUrlAction;
import io.miranum.platform.engine.domain.process.StartContext;
import io.miranum.platform.engine.shared.file.AbstractFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Service to handle files in service starts in DigiWF.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Service
public class GetPresignedUrlForProcessStartService extends AbstractFileService implements GetPresignedUrlForProcessStartUseCase, GetFileNamesForProcessStartUseCase {

    private final StartContextPort startContextPort;
    private final ProcessConfigPort processConfigPort;

    public GetPresignedUrlForProcessStartService(
            final DocumentStorageFolderRepository documentStorageFolderRepository,
            final StartContextPort serviceStartContextService,
            final ProcessConfigPort processConfigPort,
            final List<PresignedUrlAdapter> presignedUrlAdapters,
            final ProcessConfigFunctions processConfigFunctions
    ) {
        super(documentStorageFolderRepository, presignedUrlAdapters, processConfigFunctions);
        this.startContextPort = serviceStartContextService;
        this.processConfigPort = processConfigPort;
    }

    public List<String> getFileNames(final String definitionKey, final String filePath, final String userId, final List<String> groups) {
        this.checkReadAccess(definitionKey, filePath);
        final String fileContext = this.getFileContext(userId, definitionKey);
        return super.getFileNames(filePath, fileContext, this.getDocumentStorageUrl(definitionKey));
    }

    public String getPresignedUrl(final PresignedUrlAction action, final String definitionKey, final String filePath, final String fileName, final String userId, final List<String> groups) {
        if (action.equals(PresignedUrlAction.GET)) {
            this.checkReadAccess(definitionKey, filePath);
        } else {
            this.checkWriteAccess(definitionKey, filePath);
        }

        final String fileContext = this.getFileContext(userId, definitionKey);
        return super.getPresignedUrl(action, fileContext + "/" + filePath + "/" + fileName, this.getDocumentStorageUrl(definitionKey));
    }

    //---------------------------------------- helper methods ---------------------------------------- //

    private String getFileContext(final String userId, final String definitionKey) {
        return this.startContextPort.searchStartContext(userId, definitionKey)
                .map(StartContext::getId)
                .orElseThrow(() -> new RuntimeException("No file context found for task"));
    }

    private void checkReadAccess(final String identifier, final String filePath) {
        try {
            this.checkWriteAccess(identifier, filePath);
        } catch (final IllegalResourceAccessException ex) {
            final String filePathsReadonly = this.processConfigPort.getByRef(identifier).getFilePathsReadonly();
            if (StringUtils.isEmpty(filePathsReadonly)) {
                throw new IllegalResourceAccessException(ERRTEXT_ILLEGAL_ACCESS);
            }

            Arrays.stream(filePathsReadonly.split(FILEPATH_DELIMITER))
                    .filter(filePath::startsWith)
                    .findFirst()
                    .orElseThrow(() -> new IllegalResourceAccessException(ERRTEXT_ILLEGAL_ACCESS));
        }
    }

    private void checkWriteAccess(final String identifier, final String filePath) {

        final String filePaths = this.processConfigPort.getByRef(identifier).getFilePaths();
        if (StringUtils.isEmpty(filePaths)) {
            throw new IllegalResourceAccessException(ERRTEXT_ILLEGAL_ACCESS);
        }

        Arrays.stream(filePaths.split(FILEPATH_DELIMITER))
                .filter(filePath::startsWith)
                .findFirst()
                .orElseThrow(() -> new IllegalResourceAccessException(ERRTEXT_ILLEGAL_ACCESS));

    }

}
