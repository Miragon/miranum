package io.miranum.platform.connect.json.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class ValidationResult {

    private final String type;
    private final String code;
    private final String path;
    private final String schemaPath;
    private final String[] arguments;
    private final Map<String, Object> details;
    private final String message;

}
