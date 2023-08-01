package io.miranum.integration.s3.application;

import io.miranum.integration.s3.adapter.out.persistence.File;
import io.miranum.integration.s3.adapter.out.persistence.FileRepository;
import io.miranum.integration.s3.adapter.out.s3.S3Repository;
import io.miranum.integration.s3.application.CleanUpExpiredFilesUseCase;
import io.miranum.integration.s3.application.port.in.FileSystemAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CleanUpExpiredFilesUseCaseTest {

  @Mock
  private FileRepository fileRepository;

  @Mock
  private S3Repository s3Repository;

  private CleanUpExpiredFilesUseCase cleanUpExpiredFilesUseCase;

  @BeforeEach
  public void beforeEach() {
    this.cleanUpExpiredFilesUseCase = new CleanUpExpiredFilesUseCase(this.fileRepository, this.s3Repository);
  }

  @Test
  void cleanUp() throws FileSystemAccessException {
    final var file1 = new File();
    file1.setPathToFile("file1");
    file1.setEndOfLife(LocalDate.now().minusYears(1));
    final var file2 = new File();
    file2.setPathToFile("file2");
    file2.setEndOfLife(LocalDate.now().minusYears(1));
    final var file3 = new File();
    file3.setPathToFile("file3");
    file3.setEndOfLife(LocalDate.now().minusYears(1));
    final Stream<File> folderStream = Stream.of(file1, file2, file3);

    Mockito.when(this.fileRepository.findAllByEndOfLifeNotNullAndEndOfLifeBefore(Mockito.any(LocalDate.class)))
        .thenReturn(folderStream);
    this.cleanUpExpiredFilesUseCase.cleanUpExpiredFolders();
    Mockito.verify(this.s3Repository, Mockito.times(3)).deleteFile(Mockito.anyString());
  }

}
