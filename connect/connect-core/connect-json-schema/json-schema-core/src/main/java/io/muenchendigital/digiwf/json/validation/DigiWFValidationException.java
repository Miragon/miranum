package io.muenchendigital.digiwf.json.validation;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DigiWFValidationException extends RuntimeException {
    private List<ValidationErrorInformation> validationErrorInformation;

    public DigiWFValidationException(List<ValidationErrorInformation> validationErrorInformation) {
        super(validationErrorInformation.stream().map(ValidationErrorInformation::toString).collect(Collectors.joining("\n")));
        this.validationErrorInformation = validationErrorInformation;
    }
}
