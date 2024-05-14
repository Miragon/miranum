package io.miragon.miranum.connect.s3.application.port.out;

import io.minio.http.Method;
import io.miragon.miranum.connect.s3.domain.model.FileSystemAccessException;

import java.util.Set;

public interface S3Repository {
    Set<String> getFilePathsFromFolder(String folder) throws FileSystemAccessException;

    void deleteFile(String pathToFile) throws FileSystemAccessException;

    String getPresignedUrl(String pathToFile, Method action, int expiresInMinutes) throws FileSystemAccessException;
}
