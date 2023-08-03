package io.miranum.platform.tasklist.application.port.in;

import io.miranum.platform.tasklist.domain.PresignedUrlAction;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;

public interface WorkOnTaskFile {

    /**
     * Get file names for task and fieldKey.
     * @param taskId task id
     * @param filePath filepath
     * @return list of filenames
     * @throws AccessDeniedException if the user has no access.
     */
    @NonNull
    List<String> getFileNames(@NonNull final String taskId, @NonNull final String filePath) throws AccessDeniedException;

    /**
     * Get a presigned url to load, upload or delete a file for a specific field key and file name
     * @param action GET, PUT or DELETE
     * @param taskId task id
     * @param filePath filepath
     * @param fileName filename
     * @return PresignedUrl as string.
     * @throws AccessDeniedException if the user has no access.
     */
    @NonNull
    String getPresignedUrl(@NonNull final PresignedUrlAction action, @NonNull final String taskId, @NonNull final String filePath, @NonNull final String fileName) throws AccessDeniedException;
}
