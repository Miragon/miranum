package io.miranum.platform.engine.shared.file;

import de.muenchen.oss.digiwf.s3.integration.application.port.in.FolderOperationsApi;
import io.miranum.platform.engine.adapter.in.engine.ProcessConfigFunctions;
import io.miranum.platform.engine.application.port.out.file.PresignedUrlAdapter;
import io.miranum.platform.engine.domain.file.PresignedUrlAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * General logic for handling file storage.
 *
 * @author martin.dietrich
 */
@Slf4j
public abstract class AbstractFileService {

    public static final String FILEPATH_DELIMITER = ";";
    public static final String ERRTEXT_ILLEGAL_ACCESS = "No access to defined property";

    protected final FolderOperationsApi folderOperationsApi;
    private final List<PresignedUrlAdapter> presignedUrlAdapters;
    private final ProcessConfigFunctions processConfigFunctions;

    public AbstractFileService(
            final FolderOperationsApi folderOperationsApi,
            final List<PresignedUrlAdapter> presignedUrlAdapters,
            final ProcessConfigFunctions processConfigFunctions
    ) {
        this.folderOperationsApi = folderOperationsApi;
        this.presignedUrlAdapters = presignedUrlAdapters;
        this.processConfigFunctions = processConfigFunctions;
    }

    public List<String> getFileNames(final String filePath, final String fileContext, final Optional<String> documentStorageUrl) {
        try {
            if (documentStorageUrl.isPresent()) {
                return this.removeFolderFromPaths(this.folderOperationsApi.getAllFilesInFolderRecursively(fileContext + "/" + filePath).getPathToFiles());
            }
            return this.removeFolderFromPaths(this.folderOperationsApi.getAllFilesInFolderRecursively(fileContext + "/" + filePath).getPathToFiles());
        } catch (final Exception ex) {
            log.error("Getting all files of folder {} failed: {}", filePath, ex);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Getting all files of folder %s failed", filePath));
        }
    }

    protected String getPresignedUrl(final PresignedUrlAction action, final String pathToFile, final Optional<String> documentStorageUrl) {
        final Optional<PresignedUrlAdapter> handler = this.presignedUrlAdapters.stream()
                .filter(h -> h.isResponsibleForAction(action))
                .findAny();
        if (handler.isPresent()) {
            if (documentStorageUrl.isPresent()) {
                return handler.get().getPresignedUrl(documentStorageUrl.get(), pathToFile, 5);
            }
            return handler.get().getPresignedUrl(pathToFile, 5);
        }
        log.warn("No handler specified for action {}", action);
        throw new RuntimeException(String.format("No handler specified for action %s", action));
    }

    protected Optional<String> getDocumentStorageUrl(final String definitionKey) {
        return Optional.ofNullable(this.processConfigFunctions.get("app_file_s3_sync_config", definitionKey));
    }

    //---------------------------------------- helper methods ---------------------------------------- //

    private List<String> removeFolderFromPaths(final Set<String> fileList) {
        return fileList.stream()
                .map(file -> file.substring(file.lastIndexOf("/") + 1))
                .collect(Collectors.toList());
    }

}
