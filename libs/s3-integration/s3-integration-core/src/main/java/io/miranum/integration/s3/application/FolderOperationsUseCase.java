package io.miranum.integration.s3.application;

import io.miranum.integration.s3.adapter.in.rest.validation.FolderInFilePathValidator;
import io.miranum.integration.s3.adapter.out.persistence.File;
import io.miranum.integration.s3.adapter.out.persistence.FileRepository;
import io.miranum.integration.s3.adapter.out.s3.S3Repository;
import io.miranum.integration.s3.application.port.in.FileSystemAccessException;
import io.miranum.integration.s3.application.port.in.FolderOperationsInPort;
import io.miranum.integration.s3.model.FilesInFolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FolderOperationsUseCase implements FolderOperationsInPort {

  private final S3Repository s3Repository;
  private final FileRepository fileRepository;

  /**
   * Deletes the folder with all containing files specified in the parameter together with the corresponding database entries.
   *
   * @param pathToFolder identifies the path to the folder.
   * @throws FileSystemAccessException if the S3 storage cannot be accessed.
   */
  @Transactional
  @Override
  public void deleteFolder(@NotNull final String pathToFolder) throws FileSystemAccessException {
    final String pathToFolderWithSeparatorAtTheEnd = addPathSeparatorToTheEnd(pathToFolder);
    final Set<String> filePathsInDatabase = this.fileRepository.findByPathToFileStartingWith(pathToFolderWithSeparatorAtTheEnd)
        .map(File::getPathToFile)
        .collect(Collectors.toSet());
    final Set<String> filePathsInFolder = this.s3Repository.getFilePathsFromFolder(pathToFolderWithSeparatorAtTheEnd);
    if (filePathsInDatabase.isEmpty() && filePathsInFolder.isEmpty()) {
      log.info("Folder in S3 and file entities in database for this folder does not exist -> everything ok.");
    } else if (SetUtils.isEqualSet(filePathsInDatabase, filePathsInFolder)) {
      // Delete all files on S3
      log.info("All ${} files in folder ${} will be deleted.", filePathsInFolder.size(), pathToFolderWithSeparatorAtTheEnd);
      for (final String pathToFile : filePathsInFolder) {
        // Delete file on S3
        this.s3Repository.deleteFile(pathToFile);
        // Delete database entry
        this.fileRepository.deleteByPathToFile(pathToFile);
      }
      log.info("All ${} files in folder ${} will be deleted..", filePathsInFolder.size(), pathToFolderWithSeparatorAtTheEnd);
    } else {
      // Out of sync
      final Set<String> filePathDisjunction = SetUtils.disjunction(filePathsInDatabase, filePathsInFolder).toSet();
      final StringBuilder message = new StringBuilder(String.format("The following files on S3 and the file entities in database for folder %s are out of sync.\n", pathToFolderWithSeparatorAtTheEnd));
      filePathDisjunction.stream()
          .map(pathToFile -> pathToFile.concat("\n"))
          .forEach(message::append);
      log.error(message.toString());
      throw new FileSystemAccessException(message.toString());
    }
  }

  /**
   * Returns all files identified by file paths for all files contained within the folder and subfolder recursively.
   *
   * @param pathToFolder identifies the path to the folder.
   * @return the paths to the files within the folder and subfolder.
   * @throws FileSystemAccessException if the S3 storage cannot be accessed.
   */
  @NotNull
  @Override
  public FilesInFolder getAllFilesInFolderRecursively(@NotNull final String pathToFolder) throws FileSystemAccessException {
    final String pathToFolderWithSeparatorAtTheEnd = addPathSeparatorToTheEnd(pathToFolder);
    final FilesInFolder filesInFolder = new FilesInFolder();
    final Set<String> filePathsInFolder = this.s3Repository.getFilePathsFromFolder(pathToFolderWithSeparatorAtTheEnd);
    filesInFolder.setPathToFiles(filePathsInFolder);
    return filesInFolder;
  }

  /**
   * The method adds a path separator to the end of the parameter if no separator is already added.
   *
   * @param pathToFolder to add a separator.
   * @return the path to folder
   */
  public static String addPathSeparatorToTheEnd(final String pathToFolder) {
    String correctedPathToFolder = pathToFolder;
    if (StringUtils.isNotEmpty(pathToFolder) &&
        !StringUtils.endsWith(pathToFolder, FolderInFilePathValidator.SEPARATOR)) {
      correctedPathToFolder = correctedPathToFolder + FolderInFilePathValidator.SEPARATOR;
    }
    return correctedPathToFolder;
  }

}
