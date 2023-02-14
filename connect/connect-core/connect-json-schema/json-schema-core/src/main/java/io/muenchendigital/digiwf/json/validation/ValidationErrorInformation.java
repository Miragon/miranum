package io.muenchendigital.digiwf.json.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.everit.json.schema.Schema;

@ToString
@Getter
@AllArgsConstructor
public class ValidationErrorInformation {

    private String pointer;

    private String schemaPath;

    private Schema violatedSchema;

    private String message;

}
