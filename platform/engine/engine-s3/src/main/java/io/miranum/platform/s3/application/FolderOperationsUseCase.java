package io.miranum.platform.s3.application;

import io.miranum.platform.s3.adapter.out.persistence.File;
import io.miranum.platform.s3.application.port.in.FolderOperationsApi;
import io.miranum.platform.s3.application.port.out.FileRepository;
import io.miranum.platform.s3.application.port.out.S3Repository;
import io.miranum.platform.s3.domain.model.FileSystemAccessException;
import io.miranum.platform.s3.domain.model.FilesInFolder;
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
@Transactional
@RequiredArgsConstructor
public class FolderOperationsUseCase implements FolderOperationsApi {

    private final S3Repository s3Repository;
    private final FileRepository fileRepository;

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


    @Override
    public FilesInFolder getAllFilesInFolderRecursively(@NotNull final String pathToFolder) throws FileSystemAccessException {
        final String pathToFolderWithSeparatorAtTheEnd = addPathSeparatorToTheEnd(pathToFolder);
        final FilesInFolder filesInFolder = new FilesInFolder();
        final Set<String> filePathsInFolder = this.s3Repository.getFilePathsFromFolder(pathToFolderWithSeparatorAtTheEnd);
        filesInFolder.setPathToFiles(filePathsInFolder);
        return filesInFolder;
    }

    public static String addPathSeparatorToTheEnd(final String pathToFolder) {
        String correctedPathToFolder = pathToFolder;
        if (StringUtils.isNotEmpty(pathToFolder) &&
                !StringUtils.endsWith(pathToFolder, FilesInFolder.SEPARATOR)) {
            correctedPathToFolder = correctedPathToFolder + FilesInFolder.SEPARATOR;
        }
        return correctedPathToFolder;
    }

}
