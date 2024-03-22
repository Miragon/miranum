package io.miranum.platform.connect.json.registry.adapter.in.handler;

import io.miranum.platform.connect.shared.ObjectNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class SchemaExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<String> handleOperationNotAllowed(final ObjectNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(final ConstraintViolationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleInternalServerError(final RuntimeException exception) {
        log.error("Internal server error:", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
    }
}
