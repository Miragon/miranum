package io.miranum.platform.tasklist.application.port.in;

import io.miranum.platform.tasklist.domain.PresignedUrlAction;

import java.util.List;

public interface WorkOnTaskFileUseCase {

    /**
     * Get file names for task and fieldKey.
     *
     * @param taskId   task id
     * @param filePath filepath
     * @return list of filenames
     */
    List<String> getFileNames(final String taskId, final String filePath);

    /**
     * Get a presigned url to load, upload or delete a file for a specific field key and file name
     *
     * @param action   GET, PUT or DELETE
     * @param taskId   task id
     * @param filePath filepath
     * @param fileName filename
     * @return PresignedUrl as string.
     */
    String getPresignedUrl(final PresignedUrlAction action, final String taskId, final String filePath, final String fileName);
}
