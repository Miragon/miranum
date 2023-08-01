package io.miranum.integration.s3.application;

import io.minio.http.Method;
import io.miranum.integration.s3.adapter.in.rest.validation.FolderInFilePathValidator;
import io.miranum.integration.s3.adapter.out.persistence.File;
import io.miranum.integration.s3.adapter.out.persistence.FileRepository;
import io.miranum.integration.s3.adapter.out.s3.S3Repository;
import io.miranum.integration.s3.application.port.in.FileExistenceException;
import io.miranum.integration.s3.application.port.in.FileOperationsInPort;
import io.miranum.integration.s3.application.port.in.FileSystemAccessException;
import io.miranum.integration.s3.model.FileData;
import io.miranum.integration.s3.model.PresignedUrl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FileOperationsUseCase implements FileOperationsInPort {

  private final S3Repository s3Repository;
  private final FileRepository fileRepository;

  /**
   * Get a list of presigned urls for all files in the paths.
   * If the path is a file the presigned url for the file is returned.
   * The end of life for the files to save is not set und therefore the files are not deleted automatically.
   *
   * @param paths            list of paths to files and/or folders
   * @param action           http method for the presigned url
   * @param expiresInMinutes presigned url expiration time
   * @return list of pre-signed urls.
   * @throws FileSystemAccessException on S3 access errors.
   * @throws FileExistenceException    if file doesn't exist.
   */
  @Override
  public List<PresignedUrl> getPresignedUrls(final List<String> paths, final Method action, final int expiresInMinutes) throws FileSystemAccessException, FileExistenceException {
    return this.getPresignedUrls(paths, action, expiresInMinutes, null);
  }

  /**
   * Get a single presigned url for the path.
   * The end of life for the files to save is not set und therefore the files are not deleted automatically.
   *
   * @param path             path to file or folder
   * @param action           http method for the presigned url
   * @param expiresInMinutes presigned url expiration time
   * @return pre-signed url.
   * @throws FileSystemAccessException on S3 access errors.
   */
  @Override
  public PresignedUrl getPresignedUrl(final String path, final Method action, final int expiresInMinutes) throws FileSystemAccessException {
    return this.getPresignedUrl(path, action, expiresInMinutes, null);
  }

  /**
   * Creates a presigned URL to fetch the file specified in the parameter from the S3 storage.
   *
   * @param pathToFile       identifies the path to file.
   * @param expiresInMinutes to define the validity period of the presigned URL.
   * @throws FileExistenceException    if the file does not exist in the folder.
   * @throws FileSystemAccessException if the S3 storage cannot be accessed.
   */
  @Override
  public PresignedUrl getFile(final String pathToFile, final int expiresInMinutes) throws FileExistenceException, FileSystemAccessException {
    if (!this.fileExists(pathToFile)) {
      final String message = String.format("The file %s does not exists.", pathToFile);
      log.error(message);
      throw new FileExistenceException(message);
    }
    return this.getPresignedUrl(pathToFile, Method.GET, expiresInMinutes);
  }

  /**
   * Creates a presigned URL to store the file specified in the parameter within the S3 storage.
   * The file must not exist yet.
   *
   * @param fileData with the file metadata to save.
   * @throws FileExistenceException    if the file already exists.
   * @throws FileSystemAccessException if the S3 storage cannot be accessed.
   */

  @Override
  public PresignedUrl saveFile(final FileData fileData) throws FileSystemAccessException, FileExistenceException {
    if (this.fileExists(fileData.getPathToFile())) {
      final String message = String.format("The file %s does exists.", fileData.getPathToFile());
      log.error(message);
      throw new FileExistenceException(message);
    }
    return this.getPresignedUrl(fileData.getPathToFile(), Method.PUT, fileData.getExpiresInMinutes(), fileData.getEndOfLife());
  }

  /**
   * Creates a presigned URL to overwrite the file specified in the parameter within the S3 storage.
   * Furthermore, the entry regarding {@link File#getEndOfLife()} is adjusted in the database.
   * <p>
   * If the file does not yet exist in the S3 storage, it is newly created and a
   * corresponding {@link File} is persisted in the database.
   *
   * @param fileData with the file metadata for re-saving.
   * @throws FileSystemAccessException if the S3 storage cannot be accessed.
   */
  @Override
  public PresignedUrl updateFile(final FileData fileData) throws FileSystemAccessException {
    return this.getPresignedUrl(fileData.getPathToFile(), Method.PUT, fileData.getExpiresInMinutes(), fileData.getEndOfLife());
  }

  /**
   * Updates the end of life for the given file.
   *
   * @param pathToFile identifies the path to file.
   * @param endOfLife  the files endOfLife. May be null. If null, no end of life is set
   * @throws FileExistenceException if no database entry exists.
   */
  @Override
  public void updateEndOfLife(final String pathToFile, final LocalDate endOfLife) throws FileExistenceException {
    final Optional<File> fileOptional = this.fileRepository.findByPathToFile(pathToFile);
    if (fileOptional.isPresent()) {
      final File file = fileOptional.get();
      file.setEndOfLife(endOfLife);
      this.fileRepository.save(file);
      log.info("End of life updated for file ${} to ${}", file, endOfLife);
    } else {
      final String message = String.format("No database entry for file %s is found.", pathToFile);
      log.error(message);
      throw new FileExistenceException(message);
    }
  }

  /**
   * Creates a presigned URL to delete the file specified in the parameter from the S3 storage.
   *
   * @param pathToFile       identifies the path to file.
   * @param expiresInMinutes to define the validity period of the presigned URL.
   * @throws FileExistenceException    if the file does not exist in the folder.
   * @throws FileSystemAccessException if the S3 storage cannot be accessed.
   */
  @Override
  public PresignedUrl deleteFile(final String pathToFile, final int expiresInMinutes) throws FileExistenceException, FileSystemAccessException {
    if (!this.fileExists(pathToFile)) {
      final String message = String.format("The file %s does not exists.", pathToFile);
      log.error(message);
      throw new FileExistenceException(message);
    }
    return this.getPresignedUrl(pathToFile, Method.DELETE, expiresInMinutes);
  }

  /**
   * Get a single presigned url for the path
   *
   * @param path             path to file or folder
   * @param action           http method for the presigned url
   * @param expiresInMinutes presigned url expiration time
   * @param endOfLife        the files endOfLife. May be null. If null, no end of life is set
   * @return pre-signed url.
   * @throws FileSystemAccessException on S3 access errors.
   */
  private PresignedUrl getPresignedUrl(final String path, final Method action, final int expiresInMinutes, final LocalDate endOfLife) throws FileSystemAccessException {
    // make sure the file exists before saving files
    if (action.equals(Method.PUT) || action.equals(Method.POST)) {
      this.setupFile(path, endOfLife);
    }
    return new PresignedUrl(this.s3Repository.getPresignedUrl(path, action, expiresInMinutes), path, action.toString());
  }

  /**
   * Creates a new file entity within the database, if no entity identified by pathToFile is available.
   * Otherwise, the existing file entity is updated with endOfLife.
   *
   * @param pathToFile for which a file entity should be set up in the database
   * @param endOfLife  the files endOfLife. May be null. If null, no end of life is set
   */
  private void setupFile(final String pathToFile, final LocalDate endOfLife) {
    final Optional<File> fileOptional = this.fileRepository.findByPathToFile(pathToFile);
    if (fileOptional.isEmpty()) {
      log.info("The database entry for file ${} does not exist.", pathToFile);
      final var folder = new File();
      folder.setPathToFile(pathToFile);
      folder.setEndOfLife(endOfLife);
      this.fileRepository.save(folder);
    } else {
      log.info("The database entry for file ${} already exists.", pathToFile);
      final File folder = fileOptional.get();
      folder.setEndOfLife(endOfLife);
      this.fileRepository.save(folder);
    }
  }

  private boolean fileExists(final String filePath) throws FileSystemAccessException {
    final String pathToFolder = getPathToFolder(filePath);
    final Set<String> filePathsInFolder = this.s3Repository.getFilePathsFromFolder(pathToFolder);
    // if file does not exist throw an error
    return filePathsInFolder.contains(filePath);
  }

  /**
   * Get a list of presigned urls for all files in the path.
   * If the path is a file the presigned url for the file is returned.
   *
   * @param path             path to file or folder
   * @param action           http method for the presigned url
   * @param expiresInMinutes presigned url expiration time
   * @param endOfLife        the files endOfLife. May be null. If null, no end of life is set
   * @return list of pre-signed urls.
   * @throws FileSystemAccessException on S3 access errors.
   * @throws FileExistenceException    if file doesn't exist.
   */
  private List<PresignedUrl> getPresignedUrls(final String path, final Method action, final int expiresInMinutes, final LocalDate endOfLife) throws FileSystemAccessException, FileExistenceException {
    // make sure the folder exists before saving files
    if (action.equals(Method.PUT) || action.equals(Method.POST)) {
      this.setupFile(path, endOfLife);
    }

    // special case file creation (POST)
    // Use method PUT and return a single presignedUrl for the file the user wants to create
    if (action.equals(Method.POST)) {
      return List.of(this.getPresignedUrl(path, Method.PUT, expiresInMinutes, endOfLife));
    }

    // PUT, GET, DELETE return single presignedUrl if path is file. Return list of presignedUrls if path is directory
    final List<String> paths = new ArrayList<>(this.s3Repository.getFilePathsFromFolder(path));
    final List<PresignedUrl> presignedUrlList = paths.stream()
        .map(filePath -> this.getPresignedUrlForFile(filePath, action, expiresInMinutes))
        .filter(Objects::nonNull)
        .collect(Collectors.toList());

    if (presignedUrlList.isEmpty()) {
      final String message = String.format("The file %s does not exist.", path);
      log.error(message);
      throw new FileExistenceException(message);
    }

    return presignedUrlList;
  }

  /**
   * Get a list of presigned urls for all files in the paths.
   * If the path is a file the presigned url for the file is returned.
   *
   * @param paths            list of paths to files and/or folders
   * @param action           http method for the presigned url
   * @param expiresInMinutes presigned url expiration time
   * @param endOfLife        the files endOfLife. May be null. If null, no end of life is set
   * @return list of pre-signed urls.
   * @throws FileSystemAccessException on S3 access errors.
   * @throws FileExistenceException    if file doesn't exist.
   */
  private List<PresignedUrl> getPresignedUrls(final List<String> paths, final Method action, final int expiresInMinutes, LocalDate endOfLife) throws FileSystemAccessException, FileExistenceException {
    final List<PresignedUrl> presignedUrls = new ArrayList<>();
    for (String p : paths) {
      presignedUrls.addAll(this.getPresignedUrls(p, action, expiresInMinutes, endOfLife));
    }
    return presignedUrls;
  }

  private PresignedUrl getPresignedUrlForFile(final String filePath, final Method action, final int expiresInMinutes) {
    try {
      final String presignedUrl = this.s3Repository.getPresignedUrl(filePath, action, expiresInMinutes);
      return new PresignedUrl(presignedUrl, filePath, action.toString());
    } catch (final FileSystemAccessException e) {
      log.warn("File not found on path {}", filePath);
    }
    return null;
  }

  /**
   * Return the path to the folder for the given file path in the parameter.
   * <p>
   * pathToFile: FOLDER/SUBFOLDER/file.txt
   * pathToFolder: FOLDER/SUBFOLDER
   *
   * @param pathToFile for which the path to folder should be returned.
   * @return the path to the folder for the given path to file.
   */
  public static String getPathToFolder(final String pathToFile) {
    return StringUtils.contains(pathToFile, FolderInFilePathValidator.SEPARATOR)
        ? StringUtils.substringBeforeLast(pathToFile, FolderInFilePathValidator.SEPARATOR)
        : StringUtils.EMPTY;
  }

}
