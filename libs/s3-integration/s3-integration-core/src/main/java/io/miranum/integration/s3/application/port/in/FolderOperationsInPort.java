package io.miranum.integration.s3.application.port.in;

import io.miranum.integration.s3.model.FilesInFolder;
import org.springframework.lang.NonNull;

/**
 * Describes operations on a folder.
 */
public interface FolderOperationsInPort {
  /**
   * Retrieives a list of files in a folder.
   * @param pathToFolder path to folder.
   * @return list of files in folder.
   * @throws FileSystemAccessException on access errors.
   */
  @NonNull
  FilesInFolder getAllFilesInFolderRecursively(@NonNull String pathToFolder) throws FileSystemAccessException;

  void deleteFolder(@NonNull String pathToFolder) throws FileSystemAccessException;
}
