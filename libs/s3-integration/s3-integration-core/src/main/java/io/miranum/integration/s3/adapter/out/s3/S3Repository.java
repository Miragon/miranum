package io.miranum.integration.s3.adapter.out.s3;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import io.miranum.integration.s3.application.port.in.FileSystemAccessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IteratorUtils;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
public class S3Repository {

  private final String bucketName;
  private final String s3Url;
  private final Optional<String> s3ProxyUrl;
  private final MinioClient client;

  /**
   * Ctor.
   *
   * @param bucketName              to which this Repository should connect.
   * @param client                  to communicate with the s3 storage.
   * @param s3InitialConnectionTest to enable initial connection test to the s3 storage when true.
   * @throws FileSystemAccessException if the initial connection test fails.
   */
  public S3Repository(
      final String bucketName,
      final String s3Url,
      final MinioClient client,
      final boolean s3InitialConnectionTest,
      final Optional<String> s3ProxyUrl
  ) throws FileSystemAccessException {
    this.bucketName = bucketName;
    this.s3ProxyUrl = s3ProxyUrl;
    this.s3Url = s3Url;
    this.client = client;
    if (s3InitialConnectionTest) {
      this.initialConnectionTest(bucketName, client);
    }
  }

  /**
   * Returns the paths to the files in a given folder.
   *
   * @param folder The folder.
   *               The path must be absolute and without specifying the bucket.
   *               Example 1:
   *               Folder in bucket: "BUCKET/folder"
   *               Specification in parameter: "folder"
   *               Example 2:
   *               Folder in bucket: "BUCKET/folder/subfolder"
   *               Specification in parameter: "folder/subfolder"
   * @return the paths to the files in a given folder. Also returns the paths to the files in subfolders.
   * @throws FileSystemAccessException if the paths cannot be downloaded.
   */
  public Set<String> getFilePathsFromFolder(final String folder) throws FileSystemAccessException {
    try {
      final ListObjectsArgs listObjectsArgs = ListObjectsArgs.builder()
          .bucket(this.bucketName)
          .prefix(folder)
          .recursive(true)
          .build();
      final List<Result<Item>> resultItemList = IteratorUtils.toList(this.client.listObjects(listObjectsArgs).iterator());
      final Set<String> filepathesFromFolder = new HashSet<>();
      for (final Result<Item> resultItem : resultItemList) {
        filepathesFromFolder.add(resultItem.get().objectName());
      }
      return filepathesFromFolder;
    } catch (final MinioException | InvalidKeyException | NoSuchAlgorithmException | IllegalArgumentException |
                   IOException exception) {
      final String message = String.format("Failed to extract file paths from folder %s.", folder);
      log.error(message, exception);
      throw new FileSystemAccessException(message, exception);
    }
  }

  /**
   * Deletes the file given in the parameter.
   *
   * @param pathToFile The path to the file.
   *                   The path must be absolute and without specifying the bucket.
   *                   Example:
   *                   File in bucket: "BUCKET/outerFolder/innerFolder/thefile.csv"
   *                   Specification in parameter: "outerFolder/innerFolder/thefile.csv"
   * @throws FileSystemAccessException if the file cannot be deleted.
   */
  public void deleteFile(final String pathToFile) throws FileSystemAccessException {
    try {
      final RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
          .bucket(this.bucketName)
          .object(pathToFile)
          .build();
      this.client.removeObject(removeObjectArgs);
    } catch (final InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
                   | InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
                   | IllegalArgumentException | IOException exception) {
      final String message = String.format("Failed to delete file %s.", pathToFile);
      log.error(message, exception);
      throw new FileSystemAccessException(message, exception);
    }
  }

  /**
   * Creates the presigned URL fora file to the given file path.
   *
   * @param pathToFile       The path to the file.
   *                         The path must be absolute and without specifying the bucket.
   *                         Example:
   *                         File in bucket: "BUCKET/outerFolder/innerFolder/thefile.csv"
   *                         Specification in parameter: "outerFolder/innerFolder/thefile.csv"
   * @param action           to determine the file permissions.
   * @param expiresInMinutes to define the validity period of the presigned URL.
   * @return the presigned URL for a file.
   * @throws FileSystemAccessException if the presigned URL cannot be created.
   */
  public String getPresignedUrl(final String pathToFile, final Method action, final int expiresInMinutes) throws FileSystemAccessException {
    try {
      final GetPresignedObjectUrlArgs presignedUrlArgs = GetPresignedObjectUrlArgs.builder()
          .method(action)
          .bucket(this.bucketName)
          .object(pathToFile)
          .expiry(expiresInMinutes, TimeUnit.MINUTES)
          .build();
      final String presignedUrl = this.client.getPresignedObjectUrl(presignedUrlArgs);
      // use proxy if enabled
      return this.s3ProxyUrl.map(proxy -> presignedUrl.replaceFirst(this.s3Url, proxy)).orElse(presignedUrl);
    } catch (final InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
                   | InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
                   | IllegalArgumentException | IOException exception) {
      final String message = String.format("Failed to create presigned url for file %s. in mode %s", pathToFile, action);
      log.error(message, exception);
      throw new FileSystemAccessException(message, exception);
    }
  }

  /**
   * Performs an initial connection test against the S3 storage.
   *
   * @param bucketName to which this Repository should connect.
   * @param client     to communicate with the s3 storage.
   * @throws FileSystemAccessException if the initial connection test fails.
   */
  private void initialConnectionTest(final String bucketName, final MinioClient client) throws FileSystemAccessException {
    try {
      final boolean bucketExists = client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
      if (!bucketExists) {
        final String message = "S3 bucket does not exist.";
        log.error(message);
        throw new FileSystemAccessException(message);
      }
    } catch (final MinioException | InvalidKeyException | NoSuchAlgorithmException | IllegalArgumentException |
                   IOException exception) {
      final String message = "S3 initialization failed.";
      log.error(message, exception);
      throw new FileSystemAccessException(message, exception);
    }
  }

}
