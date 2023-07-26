package io.miranum.integration.s3.application;

import io.miranum.integration.s3.adapter.out.persistence.File;
import io.miranum.integration.s3.adapter.out.persistence.FileRepository;
import io.miranum.integration.s3.adapter.out.s3.S3Repository;
import io.miranum.integration.s3.application.FolderOperationsUseCase;
import io.miranum.integration.s3.application.port.in.FileSystemAccessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FolderOperationsUseCaseTest {

  @Mock
  private S3Repository s3Repository;

  @Mock
  private FileRepository fileRepository;

  private FolderOperationsUseCase folderHandlingService;

  @BeforeEach
  public void beforeEach() {
    this.folderHandlingService = new FolderOperationsUseCase(this.s3Repository, this.fileRepository);
    Mockito.reset(this.s3Repository);
    Mockito.reset(this.fileRepository);
  }

  @Test
  void deleteFolderException() throws FileSystemAccessException {
    final String pathToFile = "folder/file.txt";
    final String pathToFolder = "folder";
    final String pathToFolderWithSeparator = pathToFolder + "/";
    final File file = new File();
    file.setPathToFile(pathToFile);

    Mockito.when(this.s3Repository.getFilePathsFromFolder(pathToFolderWithSeparator)).thenReturn(new HashSet<>());
    Mockito.when(this.fileRepository.findByPathToFileStartingWith(pathToFolderWithSeparator)).thenReturn(Stream.of(file));
    Assertions.assertThrows(FileSystemAccessException.class, () -> this.folderHandlingService.deleteFolder(pathToFolder));

    Mockito.when(this.s3Repository.getFilePathsFromFolder(pathToFolderWithSeparator)).thenReturn(new HashSet<>(List.of(pathToFile)));
    Mockito.when(this.fileRepository.findByPathToFileStartingWith(pathToFolderWithSeparator)).thenReturn(Stream.empty());
    Assertions.assertThrows(FileSystemAccessException.class, () -> this.folderHandlingService.deleteFolder(pathToFolder));

    Mockito.when(this.s3Repository.getFilePathsFromFolder(pathToFolderWithSeparator)).thenReturn(new HashSet<>());
    Mockito.when(this.fileRepository.findByPathToFileStartingWith(pathToFolderWithSeparator)).thenReturn(Stream.empty());
    Assertions.assertDoesNotThrow(() -> this.folderHandlingService.deleteFolder(pathToFolder));
  }

  @Test
  void deleteFolder() throws FileSystemAccessException {
    final String pathToFile = "folder/file.txt";
    final String pathToFolder = "folder";
    final String pathToFolderWithSeparator = pathToFolder + "/";
    final File file = new File();
    file.setPathToFile(pathToFile);

    Mockito.when(this.s3Repository.getFilePathsFromFolder(pathToFolderWithSeparator)).thenReturn(new HashSet<>(List.of(pathToFile)));
    Mockito.when(this.fileRepository.findByPathToFileStartingWith(pathToFolderWithSeparator)).thenReturn(Stream.of(file));
    Assertions.assertDoesNotThrow(() -> this.folderHandlingService.deleteFolder(pathToFolder));
    Mockito.verify(this.fileRepository, Mockito.times(1)).deleteByPathToFile(pathToFile);
    Mockito.verify(this.s3Repository, Mockito.times(1)).deleteFile(pathToFile);
  }

  @Test
  void addPathSeparatorToTheEnd() {
    assertThat(FolderOperationsUseCase.addPathSeparatorToTheEnd("folder/subfolder"), is("folder/subfolder/"));
    assertThat(FolderOperationsUseCase.addPathSeparatorToTheEnd("folder/subfolder/"), is("folder/subfolder/"));
    assertThat(FolderOperationsUseCase.addPathSeparatorToTheEnd("folder"), is("folder/"));
    assertThat(FolderOperationsUseCase.addPathSeparatorToTheEnd("folder/"), is("folder/"));
    assertThat(FolderOperationsUseCase.addPathSeparatorToTheEnd("folder//"), is("folder//"));
    assertThat(FolderOperationsUseCase.addPathSeparatorToTheEnd(""), is(""));
    assertThat(FolderOperationsUseCase.addPathSeparatorToTheEnd(null), is(nullValue()));
  }

}
