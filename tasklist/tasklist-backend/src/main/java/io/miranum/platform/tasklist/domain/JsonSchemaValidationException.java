package io.miranum.platform.tasklist.domain;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@AllArgsConstructor
public class JsonSchemaValidationException extends RuntimeException{
        private String message;
}
