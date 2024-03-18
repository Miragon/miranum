package io.miranum.platform.s3.application;

import io.miranum.platform.s3.adapter.out.persistence.File;
import io.miranum.platform.s3.application.port.in.CleanUpUnusedFoldersApi;
import io.miranum.platform.s3.application.port.out.FileRepository;
import io.miranum.platform.s3.application.port.out.S3Repository;
import io.miranum.platform.s3.domain.model.FileSystemAccessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CleanUpUnusedFoldersUseCase implements CleanUpUnusedFoldersApi {

    private final S3Repository s3Repository;
    private final FileRepository fileRepository;

    /**
     * Cronjob scheduled method that deletes all {@link File} entities in the database
     * for which no corresponding file exists in the S3 storage.
     * <p>
     * The deletion is performed only if the file entity was created more than a month ago.
     */
    @Override
    public void cleanUpUnusedFolders() {
        log.info("Database clean up for folder without corresponding S3 folders started.");
        this.fileRepository.findAllByEndOfLifeIsNull()
                .filter(this::shouldDatabaseFileBeDeleted)
                .forEach(this::deleteFileInDatabase);
        log.info("Database clean up for folder without corresponding S3 folders finished.");
    }

    /**
     * Checks whether the file should be deleted from database.
     *
     * @param file to check
     * @return true if the file has to be deleted from the database. Otherwise false.
     */
    boolean shouldDatabaseFileBeDeleted(final File file) {
        boolean deleteDatabaseFile = false;
        try {
            final String pathToFolder = FileOperationsUseCase.getPathToFolder(file.getPathToFile());
            final Set<String> pathToFiles = this.s3Repository.getFilePathsFromFolder(pathToFolder);
            final boolean noSuchFileExistsInS3 = !pathToFiles.contains(file.getPathToFile());
            final LocalDate creationDate = file.getCreatedTime().toLocalDate();
            final boolean folderCreatedMoreThanAMonthAgo = creationDate.isBefore(LocalDate.now().minusMonths(1));
            deleteDatabaseFile = noSuchFileExistsInS3 && folderCreatedMoreThanAMonthAgo;
        } catch (final NullPointerException exception) {
            log.error("Created time in file entity not set.", exception);
        } catch (final FileSystemAccessException exception) {
            log.error("S3 storage could not be accessed.", exception);
        } catch (final Exception exception) {
            log.error("Error during cleanup happened.", exception);
        }
        return deleteDatabaseFile;
    }

    void deleteFileInDatabase(final File file) {
        this.fileRepository.deleteById(file.getId());
    }

}
