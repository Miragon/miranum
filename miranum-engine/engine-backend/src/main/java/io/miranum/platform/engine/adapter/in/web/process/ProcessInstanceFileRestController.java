/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.miranum.platform.engine.adapter.in.web.process;

import io.miranum.platform.engine.api.AppAuthenticationProvider;
import io.miranum.platform.engine.application.service.GetPresignedUrlForProcessInstanceService;
import io.miranum.platform.engine.domain.file.PresignedUrlAction;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * API to perform actions on service instance files
 *
 * @author externer.dl.horn
 */
@RestController
@Transactional
@RequestMapping("/rest/service/instance/file")
@RequiredArgsConstructor
@Tag(name = "ServiceInstanceFileRestController", description = "API to perform actions on service instance files")
public class ProcessInstanceFileRestController {

    private final GetPresignedUrlForProcessInstanceService getPresignedUrlForProcessInstanceService;
    private final AppAuthenticationProvider authenticationProvider;

    /**
     * Get file names for service instance and filePath.
     *
     * @param instanceId id of the service instance
     * @param filePath   Key of the field in which the base path is saved
     * @return file names
     */
    @GetMapping("/{instanceId}")
    public ResponseEntity<List<String>> getFileNames(@PathVariable final String instanceId, @RequestParam final String filePath) {
        final List<String> fileNames = this.getPresignedUrlForProcessInstanceService.getFileNames(
                instanceId,
                filePath,
                this.authenticationProvider.getCurrentUserId());
        return ResponseEntity.ok(fileNames);
    }

    /**
     * Get a presigned url to load a file for a specific field key and file name.
     *
     * @param instanceId Id of the service instance
     * @param filePath   Key of the field in which the base path is saved
     * @param fileName   Name of the file
     * @return presignedUrl
     */
    @GetMapping("/{instanceId}/{fileName}")
    public ResponseEntity<String> getPresignedUrlForFileDownload(@PathVariable final String instanceId, @PathVariable final String fileName, @RequestParam final String filePath) {
        final String presignedUrl = this.getPresignedUrlForProcessInstanceService.getPresignedUrl(
                PresignedUrlAction.GET,
                instanceId,
                filePath,
                fileName,
                this.authenticationProvider.getCurrentUserId());
        return ResponseEntity.ok(presignedUrl);
    }

    /**
     * Get a presigned url to upload a file for a specific field key and file name.
     *
     * @param instanceId Id of the service instance
     * @param filePath   Key of the field in which the base path is saved
     * @param filename   Name of the file
     * @return presignedUrl
     */
    @PostMapping("/{instanceId}/{filename}")
    public ResponseEntity<String> getPresignedUrlForFileUpload(@PathVariable final String instanceId, @PathVariable final String filename, @RequestParam final String filePath) {
        final String presignedUrls = this.getPresignedUrlForProcessInstanceService.getPresignedUrl(
                PresignedUrlAction.POST,
                instanceId,
                filePath,
                filename,
                this.authenticationProvider.getCurrentUserId());
        return ResponseEntity.ok(presignedUrls);
    }

    /**
     * Get a presigned url to delete file for a specific field key and file name.
     *
     * @param instanceId Id of the service instance
     * @param filePath   Key of the field in which the base path is saved
     * @param filename   Name of the file
     * @return presignedUrl
     */
    @DeleteMapping("/{instanceId}/{filename}")
    public ResponseEntity<String> getPresignedUrlForFileDeletion(@PathVariable final String instanceId, @PathVariable final String filename, @RequestParam final String filePath) {
        final String presignedUrl = this.getPresignedUrlForProcessInstanceService.getPresignedUrl(
                PresignedUrlAction.DELETE,
                instanceId,
                filePath,
                filename,
                this.authenticationProvider.getCurrentUserId());
        return ResponseEntity.ok(presignedUrl);
    }

}

