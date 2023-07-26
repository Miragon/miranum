package io.miranum.integration.s3.adapter.in.rest;

import io.miranum.integration.s3.application.port.in.FileExistenceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionMapping {

  @ExceptionHandler
  public ResponseEntity<String> handleFileExistenceException(FileExistenceException exception) {
    return ResponseEntity.badRequest().body(exception.getMessage());
  }

}
