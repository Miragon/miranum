/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.json.validation;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.everit.json.schema.regexp.RE2JRegexpFactory;
import org.json.JSONObject;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Json Schema Validator
 */
public class JsonSchemaValidator {

    /**
     * Validates data against a json schema
     *
     * @param schema schema that is used for validation
     * @param data   data that is validated
     */
    public void validate(final Map<String, Object> schema, final Map<String, Object> data) {
        try {
            this.validate(schema, new JSONObject(data));
        } catch (final ValidationException validationException) {
            final List<ValidationErrorInformation> errorInformation = this.extractValidationErrorInformation(validationException);
            throw new DigiWFValidationException(errorInformation);
        }
    }

    /**
     * Extract root cause exception error information
     *
     * @param validationException Validation Exception
     * @return Extracted validation error information
     */
    public List<ValidationErrorInformation> extractValidationErrorInformation(final ValidationException validationException) {
        final List<ValidationErrorInformation> errors = validationException.getCausingExceptions()
                .stream().map(this::extractValidationErrorInformation)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        final ValidationErrorInformation validationErrorInformation = new ValidationErrorInformation(
                validationException.getPointerToViolation(),
                validationException.getSchemaLocation(),
                validationException.getViolatedSchema(),
                validationException.getMessage()
        );
        errors.add(validationErrorInformation);
        return errors;
    }


    //------------------------------------- helper methods -------------------------------------//

    private void validate(final Map<String, Object> schemaObject, final JSONObject data) {
        final Schema schema = this.createSchema(new JSONObject(schemaObject));
        schema.validate(data);
    }

    private Schema createSchema(final JSONObject schemaObject) {
        return SchemaLoader.builder().schemaJson(schemaObject)
                .draftV7Support()
                .regexpFactory(new RE2JRegexpFactory())
                .build()
                .load()
                .build();
    }
}
