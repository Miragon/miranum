/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.miranum.platform.engine.adapter.in.web.process;

import io.miranum.platform.engine.api.AppAuthenticationProvider;
import io.miranum.platform.engine.application.port.in.process.GetFileNamesForProcessStartUseCase;
import io.miranum.platform.engine.application.service.GetPresignedUrlForProcessStartService;
import io.miranum.platform.engine.domain.file.PresignedUrlAction;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * API to perform actions on start service definition files
 *
 * @author externer.dl.horn
 */
@RestController
@Transactional
@RequestMapping("/rest/service/start/file")
@RequiredArgsConstructor
@Tag(name = "ServiceStartFileRestController", description = "API to perform actions on service start files")
public class ProcessStartFileRestController {

    private final GetPresignedUrlForProcessStartService getPresignedUrlForProcessStartService;
    private final GetFileNamesForProcessStartUseCase getFileNamesForProcessStartUseCase;

    private final AppAuthenticationProvider authenticationProvider;

    /**
     * Get file names for service definition and filePath.
     *
     * @param definitionKey key of the service definition
     * @param filePath      Key of the field in which the base path is saved
     * @return file names
     */
    @GetMapping("/{definitionKey}")
    public ResponseEntity<List<String>> getFileNames(@PathVariable final String definitionKey, @RequestParam final String filePath) {
        final List<String> fileNames = this.getFileNamesForProcessStartUseCase.getFileNames(
                definitionKey,
                filePath,
                this.authenticationProvider.getCurrentUserId(),
                this.authenticationProvider.getCurrentUserGroups());
        return ResponseEntity.ok(fileNames);
    }

    /**
     * Get a presigned url to load a file for a specific field key and file name.
     *
     * @param definitionKey Key of the service definition
     * @param filePath      Key of the field in which the base path is saved
     * @param fileName      Name of the file
     * @return presignedUrl
     */
    @GetMapping("/{definitionKey}/{fileName}")
    public ResponseEntity<String> getPresignedUrlForFileDownload(@PathVariable final String definitionKey, @PathVariable final String fileName, @RequestParam final String filePath) {
        final String presignedUrl = this.getPresignedUrlForProcessStartService.getPresignedUrl(
                PresignedUrlAction.GET,
                definitionKey,
                filePath,
                fileName,
                this.authenticationProvider.getCurrentUserId(),
                this.authenticationProvider.getCurrentUserGroups());
        return ResponseEntity.ok(presignedUrl);
    }

    /**
     * Get a presigned url to upload a file for a specific field key and file name.
     *
     * @param definitionKey Key of the service definition
     * @param filePath      Key of the field in which the base path is saved
     * @param filename      Name of the file
     * @return presignedUrl
     */
    @PostMapping("/{definitionKey}/{filename}")
    public ResponseEntity<String> getPresignedUrlForFileUpload(@PathVariable final String definitionKey, @PathVariable final String filename, @RequestParam final String filePath) {
        final String presignedUrls = this.getPresignedUrlForProcessStartService.getPresignedUrl(
                PresignedUrlAction.POST,
                definitionKey,
                filePath,
                filename,
                this.authenticationProvider.getCurrentUserId(),
                this.authenticationProvider.getCurrentUserGroups());
        return ResponseEntity.ok(presignedUrls);
    }

    /**
     * Get a presigned url to delete file for a specific field key and file name.
     *
     * @param definitionKey Key of the service definition
     * @param filePath      Key of the field in which the base path is saved
     * @param filename      Name of the file
     * @return presignedUrl
     */
    //TODO I guess this should be only one url?
    @DeleteMapping("/{definitionKey}/{filename}")
    public ResponseEntity<String> getPresignedUrlForFileDeletion(@PathVariable final String definitionKey, @PathVariable final String filename, @RequestParam final String filePath) {
        final String presignedUrl = this.getPresignedUrlForProcessStartService.getPresignedUrl(
                PresignedUrlAction.DELETE,
                definitionKey,
                filePath,
                filename,
                this.authenticationProvider.getCurrentUserId(),
                this.authenticationProvider.getCurrentUserGroups());
        return ResponseEntity.ok(presignedUrl);
    }

}

