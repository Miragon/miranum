/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.miranum.platform.engine.application.service;

import io.miranum.platform.engine.adapter.in.engine.ProcessConfigFunctions;
import io.miranum.platform.engine.application.port.out.file.PresignedUrlAdapter;
import io.miranum.platform.engine.application.port.out.process.MiranumProcessInstancePort;
import io.miranum.platform.engine.application.port.out.process.ProcessConfigPort;
import io.miranum.platform.engine.domain.file.IllegalResourceAccessException;
import io.miranum.platform.engine.domain.file.PresignedUrlAction;
import io.miranum.platform.engine.domain.process.MiranumProcessInstance;
import io.miranum.platform.engine.shared.file.AbstractFileService;
import io.miranum.platform.s3.application.port.in.FolderOperationsApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Service to handle files in service instances in DigiWF.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Service
public class GetPresignedUrlForProcessInstanceService extends AbstractFileService {

    private final MiranumProcessInstancePort miranumProcessInstancePort;
    private final ProcessConfigPort processConfigPort;

    public GetPresignedUrlForProcessInstanceService(
            final FolderOperationsApi folderOperationsApi,
            final List<PresignedUrlAdapter> presignedUrlAdapters,
            final ProcessConfigFunctions processConfigFunctions,
            final MiranumProcessInstancePort miranumProcessInstancePort,
            final ProcessConfigPort processConfigPort

    ) {
        super(folderOperationsApi, presignedUrlAdapters, processConfigFunctions);
        this.miranumProcessInstancePort = miranumProcessInstancePort;
        this.processConfigPort = processConfigPort;
    }

    public List<String> getFileNames(final String instanceId, final String filePath, final String userId) {
        final MiranumProcessInstance processInstance = this.getProcessInstanceId(instanceId);
        if (!this.miranumProcessInstancePort.hasAccess(instanceId, userId)) {
            throw new RuntimeException("403 returned");
        }

        this.checkReadAccess(instanceId, filePath);
        final String fileContext = this.getFileContext(instanceId);
        return super.getFileNames(filePath, fileContext, this.getDocumentStorageUrl(processInstance.getDefinitionKey()));
    }


    public String getPresignedUrl(final PresignedUrlAction action, final String instanceId, final String filePath, final String fileName, final String userId) {
        final MiranumProcessInstance processInstance = this.getProcessInstanceId(instanceId);
        final String processInstanceId = processInstance.getId();
        if (!this.miranumProcessInstancePort.hasAccess(processInstanceId, userId)) {
            throw new RuntimeException("403 returned");
        }

        if (action.equals(PresignedUrlAction.GET)) {
            this.checkReadAccess(processInstanceId, filePath);
        } else {
            this.checkWriteAccess(processInstanceId, filePath);
        }

        final String fileContext = this.getFileContext(processInstanceId);
        return super.getPresignedUrl(action, fileContext + "/" + filePath + "/" + fileName, this.getDocumentStorageUrl(processInstance.getDefinitionKey()));
    }

    //---------------------------------------- helper methods ---------------------------------------- //

    private String getFileContext(final String instanceId) {
        return this.miranumProcessInstancePort.getFileContext(instanceId);
    }

    private void checkReadAccess(final String identifier, final String filePath) {
        try {
            this.checkWriteAccess(identifier, filePath);
        } catch (final IllegalResourceAccessException ex) {
            final String defintionKey = this.getDefinitionKey(identifier);
            final String filePathsReadonly = this.processConfigPort.getByRef(defintionKey).getInstanceFilePathsReadonly();
            this.checkAccess(filePath, filePathsReadonly);
        }
    }

    private void checkWriteAccess(final String identifier, final String filePath) {
        final String defintionKey = this.getDefinitionKey(identifier);
        final String filePaths = this.processConfigPort.getByRef(defintionKey).getInstanceFilePaths();
        this.checkAccess(filePath, filePaths);
    }

    private void checkAccess(final String filePath, final String filePaths) {
        if (StringUtils.isEmpty(filePaths)) {
            throw new IllegalResourceAccessException(ERRTEXT_ILLEGAL_ACCESS);
        }

        Arrays.stream(filePaths.split(FILEPATH_DELIMITER))
                .filter(filePath::startsWith)
                .findFirst()
                .orElseThrow(() -> new IllegalResourceAccessException(ERRTEXT_ILLEGAL_ACCESS));
    }


    private String getDefinitionKey(final String instanceId) {
        return miranumProcessInstancePort.searchProcessInstanceById(instanceId)
                .map(MiranumProcessInstance::getDefinitionKey)
                .orElseThrow();
    }

    private MiranumProcessInstance getProcessInstanceId(String instanceId) {
        return miranumProcessInstancePort.searchProcessInstanceById(instanceId)
                .orElseThrow();
    }


}
