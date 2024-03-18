package de.muenchen.oss.digiwf.s3.integration.application.port.in;

import de.muenchen.oss.digiwf.s3.integration.domain.model.FileSystemAccessException;
import de.muenchen.oss.digiwf.s3.integration.domain.model.FilesInFolder;


public interface FolderOperationsApi {

    FilesInFolder getAllFilesInFolderRecursively(String pathToFolder) throws FileSystemAccessException;

    void deleteFolder(String pathToFolder) throws FileSystemAccessException;
}
