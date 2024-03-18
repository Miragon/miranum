package io.miranum.platform.s3.application.port.in;

import io.miranum.platform.s3.domain.model.FileSystemAccessException;
import io.miranum.platform.s3.domain.model.FilesInFolder;


public interface FolderOperationsApi {

    FilesInFolder getAllFilesInFolderRecursively(String pathToFolder) throws FileSystemAccessException;

    void deleteFolder(String pathToFolder) throws FileSystemAccessException;
}
