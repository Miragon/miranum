package io.miragon.miranum.connect.s3.application.port.in;

import io.miragon.miranum.connect.s3.domain.model.FileSystemAccessException;
import io.miragon.miranum.connect.s3.domain.model.FilesInFolder;


public interface FolderOperationsApi {

    FilesInFolder getAllFilesInFolderRecursively(String pathToFolder) throws FileSystemAccessException;

    void deleteFolder(String pathToFolder) throws FileSystemAccessException;
}
