package io.miranum.integration.s3.adapter.in.rest;

import io.miranum.integration.s3.adapter.in.rest.dto.FileDataDto;
import io.miranum.integration.s3.adapter.in.rest.dto.PresignedUrlDto;
import io.miranum.integration.s3.adapter.in.rest.mapper.FileDataMapper;
import io.miranum.integration.s3.adapter.in.rest.mapper.PresignedUrlMapper;
import io.miranum.integration.s3.adapter.in.rest.validation.FolderInFilePath;
import io.miranum.integration.s3.application.port.in.FileExistenceException;
import io.miranum.integration.s3.application.port.in.FileOperationsInPort;
import io.miranum.integration.s3.application.port.in.FileSystemAccessException;
import io.miranum.integration.s3.model.FileData;
import io.miranum.integration.s3.model.PresignedUrl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "FileAPI", description = "API to interact with files")
@RequestMapping("/file")
public class FileController {

  private final FileOperationsInPort fileOperations;
  private final FileDataMapper fileMapper;
  private final PresignedUrlMapper presignedUrlMapper;

  @GetMapping
  @Operation(description = "Creates a presigned URL to fetch the file specified in the parameter from the S3 storage")
  public ResponseEntity<PresignedUrlDto> get(@RequestParam @NotEmpty @Size(max = FileData.LENGTH_PATH_TO_FILE) @FolderInFilePath final String pathToFile,
                                             @RequestParam @NotNull @Min(FileData.MIN_EXPIRES_IN_MINUTES) final Integer expiresInMinutes) {
    try {
      log.info("Received a request for S3 presigned url to download a file");
      final PresignedUrl fileResponse = this.fileOperations.getFile(pathToFile, expiresInMinutes);
      final PresignedUrlDto presignedUrlDto = this.presignedUrlMapper.model2Dto(fileResponse);
      return ResponseEntity.ok(presignedUrlDto);
    } catch (final FileSystemAccessException exception) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    } catch (final FileExistenceException exception) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
    } catch (final Exception exception) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
  }

  @PostMapping
  @Operation(description = "Creates a presigned URL to store the file specified in the parameter within the S3 storage")
  public ResponseEntity<PresignedUrlDto> save(@RequestBody @NotNull @Valid final FileDataDto fileData) {
    try {
      log.info("Received a request for S3 presigned url to upload a new file");
      final PresignedUrl presignedUrl = this.fileOperations.saveFile(this.fileMapper.dto2Model(fileData));
      final PresignedUrlDto presignedUrlDto = this.presignedUrlMapper.model2Dto(presignedUrl);
      return ResponseEntity.ok(presignedUrlDto);
    } catch (final FileExistenceException exception) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage());
    } catch (final Exception exception) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }
  }

  @PutMapping
  @Operation(description = "Creates a presigned URL to overwrite the file specified in the parameter within the S3 storage")
  public ResponseEntity<PresignedUrlDto> update(@RequestBody @NotNull @Valid final FileDataDto fileData) {
    try {
      log.info("Received a request for S3 presigned url to upload a existing file");
      final PresignedUrl presignedUrl = this.fileOperations.updateFile(this.fileMapper.dto2Model(fileData));
      final PresignedUrlDto presignedUrlDto = this.presignedUrlMapper.model2Dto(presignedUrl);
      return ResponseEntity.ok(presignedUrlDto);
    } catch (final Exception exception) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }
  }

  @PatchMapping
  @Operation(description = "Updates the end of life attribute in the corresponding database entry for the file specified in the parameter")
  public ResponseEntity<Void> updateEndOfLife(@RequestParam @NotEmpty @Size(max = FileData.LENGTH_PATH_TO_FILE) @FolderInFilePath final String pathToFile,
                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate endOfLife) {
    try {
      log.info("Received a request for updating the end of life of a certain folder.");
      this.fileOperations.updateEndOfLife(pathToFile, endOfLife);
      return ResponseEntity.ok().build();
    } catch (final FileExistenceException exception) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
    } catch (final Exception exception) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping
  @Operation(description = "Creates a presigned URL to delete the file specified in the parameter from the S3 storage")
  public ResponseEntity<PresignedUrlDto> delete(@RequestParam @NotEmpty @Size(max = FileData.LENGTH_PATH_TO_FILE) @FolderInFilePath final String pathToFile,
                                                @RequestParam @NotNull @Min(FileData.MIN_EXPIRES_IN_MINUTES) final Integer expiresInMinutes) {
    try {
      log.info("Received a request for S3 presigned url to delete a file");
      final PresignedUrl presignedUrl = this.fileOperations.deleteFile(pathToFile, expiresInMinutes);
      final PresignedUrlDto presignedUrlDto = this.presignedUrlMapper.model2Dto(presignedUrl);
      return ResponseEntity.ok(presignedUrlDto);
    } catch (final FileSystemAccessException exception) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    } catch (final FileExistenceException exception) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
    } catch (final Exception exception) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
  }

}
