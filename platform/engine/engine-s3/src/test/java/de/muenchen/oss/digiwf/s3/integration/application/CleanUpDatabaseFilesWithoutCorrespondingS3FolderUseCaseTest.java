package de.muenchen.oss.digiwf.s3.integration.application;

import de.muenchen.oss.digiwf.s3.integration.adapter.out.persistence.File;
import de.muenchen.oss.digiwf.s3.integration.adapter.out.persistence.FileJpaRepository;
import de.muenchen.oss.digiwf.s3.integration.adapter.out.s3.S3Adapter;
import de.muenchen.oss.digiwf.s3.integration.domain.model.FileSystemAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CleanUpDatabaseFilesWithoutCorrespondingS3FolderUseCaseTest {

    @Mock
    private S3Adapter s3Repository;

    @Mock
    private FileJpaRepository fileRepository;

    private CleanUpUnusedFoldersUseCase cleanUpDatabaseFolderWithoutCorrespondingS3Folder;

    @BeforeEach
    public void beforeEach() {
        this.cleanUpDatabaseFolderWithoutCorrespondingS3Folder = new CleanUpUnusedFoldersUseCase(this.s3Repository, this.fileRepository);
    }

    @Test
    void shouldDatabaseFolderBeDeleted() throws FileSystemAccessException {
        final File file = new File();
        file.setPathToFile("folder/file.txt");

        // Creation date is more than one month ago.
        file.setCreatedTime(LocalDateTime.now().minusMonths(1).minusDays(1));
        Mockito.when(this.s3Repository.getFilePathsFromFolder("folder"))
                .thenReturn(new HashSet<>(List.of(file.getPathToFile())));
        assertThat(this.cleanUpDatabaseFolderWithoutCorrespondingS3Folder.shouldDatabaseFileBeDeleted(file)).isFalse();
        Mockito.when(this.s3Repository.getFilePathsFromFolder("folder"))
                .thenReturn(new HashSet<>(List.of()));
        assertThat(this.cleanUpDatabaseFolderWithoutCorrespondingS3Folder.shouldDatabaseFileBeDeleted(file)).isTrue();

        // Creation date is exactly one month or less ago.
        file.setCreatedTime(LocalDateTime.now().minusMonths(1));
        Mockito.when(this.s3Repository.getFilePathsFromFolder("folder"))
                .thenReturn(new HashSet<>(List.of(file.getPathToFile())));
        assertThat(this.cleanUpDatabaseFolderWithoutCorrespondingS3Folder.shouldDatabaseFileBeDeleted(file)).isFalse();

        Mockito.when(this.s3Repository.getFilePathsFromFolder("folder"))
                .thenReturn(new HashSet<>(List.of()));
        assertThat(this.cleanUpDatabaseFolderWithoutCorrespondingS3Folder.shouldDatabaseFileBeDeleted(file)).isFalse();
    }


}
