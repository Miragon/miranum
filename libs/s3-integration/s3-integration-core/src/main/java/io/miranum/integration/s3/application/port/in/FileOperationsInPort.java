package io.miranum.integration.s3.application.port.in;

import io.minio.http.Method;
import io.miranum.integration.s3.model.FileData;
import io.miranum.integration.s3.model.PresignedUrl;

import java.time.LocalDate;
import java.util.List;

/**
 * Port describing the main operations on files.
 */
public interface FileOperationsInPort {
  List<PresignedUrl> getPresignedUrls(List<String> paths, Method action, int expiresInMinutes) throws FileSystemAccessException, FileExistenceException;

  PresignedUrl getPresignedUrl(String path, Method action, int expiresInMinutes) throws FileSystemAccessException;

  PresignedUrl getFile(final String pathToFile, final int expiresInMinutes) throws FileExistenceException, FileSystemAccessException;

  PresignedUrl saveFile(final FileData fileData) throws FileSystemAccessException, FileExistenceException;

  PresignedUrl updateFile(final FileData fileData) throws FileSystemAccessException;

  PresignedUrl deleteFile(final String pathToFile, final int expiresInMinutes) throws FileExistenceException, FileSystemAccessException;

  void updateEndOfLife(final String pathToFile, final LocalDate endOfLife);
}
