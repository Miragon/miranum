package de.muenchen.oss.digiwf.s3.integration.application.port.out;

import de.muenchen.oss.digiwf.s3.integration.domain.model.FileSystemAccessException;
import io.minio.http.Method;

import java.util.Set;

public interface S3Repository {
    Set<String> getFilePathsFromFolder(String folder) throws FileSystemAccessException;

    void deleteFile(String pathToFile) throws FileSystemAccessException;

    String getPresignedUrl(String pathToFile, Method action, int expiresInMinutes) throws FileSystemAccessException;
}
