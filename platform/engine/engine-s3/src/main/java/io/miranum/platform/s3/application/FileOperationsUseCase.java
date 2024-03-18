package io.miranum.platform.s3.application;

import io.miranum.platform.s3.adapter.out.persistence.File;
import io.miranum.platform.s3.application.port.in.FileOperationsApi;
import io.miranum.platform.s3.application.port.out.FileRepository;
import io.miranum.platform.s3.application.port.out.S3Repository;
import io.miranum.platform.s3.domain.model.FileData;
import io.miranum.platform.s3.domain.model.FileExistenceException;
import io.miranum.platform.s3.domain.model.FileSystemAccessException;
import io.miranum.platform.s3.domain.model.PresignedUrl;
import io.minio.http.Method;
import io.miranum.platform.s3.domain.model.*;
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
public class FileOperationsUseCase implements FileOperationsApi {

    private final S3Repository s3Repository;
    private final FileRepository fileRepository;

    @Override
    public List<PresignedUrl> getPresignedUrls(final List<String> paths, final Method action, final int expiresInMinutes) throws FileSystemAccessException, FileExistenceException {
        return this.getPresignedUrls(paths, action, expiresInMinutes, null);
    }

    @Override
    public PresignedUrl getPresignedUrl(final String path, final Method action, final int expiresInMinutes) throws FileSystemAccessException {
        return this.getPresignedUrl(path, action, expiresInMinutes, null);
    }

    @Override
    public PresignedUrl getFile(final String pathToFile, final int expiresInMinutes) throws FileExistenceException, FileSystemAccessException {
        if (!this.fileExists(pathToFile)) {
            final String message = String.format("The file %s does not exists.", pathToFile);
            log.error(message);
            throw new FileExistenceException(message);
        }
        return this.getPresignedUrl(pathToFile, Method.GET, expiresInMinutes);
    }

    @Override
    public PresignedUrl saveFile(final FileData fileData) throws FileSystemAccessException, FileExistenceException {
        if (this.fileExists(fileData.getPathToFile())) {
            final String message = String.format("The file %s does exists.", fileData.getPathToFile());
            log.error(message);
            throw new FileExistenceException(message);
        }
        return this.getPresignedUrl(fileData.getPathToFile(), Method.PUT, fileData.getExpiresInMinutes(), fileData.getEndOfLife());
    }

    @Override
    public PresignedUrl updateFile(final FileData fileData) throws FileSystemAccessException {
        return this.getPresignedUrl(fileData.getPathToFile(), Method.PUT, fileData.getExpiresInMinutes(), fileData.getEndOfLife());
    }

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

    @Override
    public PresignedUrl deleteFile(final String pathToFile, final int expiresInMinutes) throws FileExistenceException, FileSystemAccessException {
        if (!this.fileExists(pathToFile)) {
            final String message = String.format("The file %s does not exists.", pathToFile);
            log.error(message);
            throw new FileExistenceException(message);
        }
        return this.getPresignedUrl(pathToFile, Method.DELETE, expiresInMinutes);
    }

    private PresignedUrl getPresignedUrl(final String path, final Method action, final int expiresInMinutes, final LocalDate endOfLife) throws FileSystemAccessException {
        // make sure the file exists before saving files
        if (action.equals(Method.PUT) || action.equals(Method.POST)) {
            this.setupFile(path, endOfLife);
        }
        return new PresignedUrl(this.s3Repository.getPresignedUrl(path, action, expiresInMinutes), path, action.toString());
    }

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


    public static String getPathToFolder(final String pathToFile) {
        return StringUtils.contains(pathToFile, FilesInFolder.SEPARATOR)
                ? StringUtils.substringBeforeLast(pathToFile, FilesInFolder.SEPARATOR)
                : StringUtils.EMPTY;
    }

}
