package io.miranum.platform.connect.elementtemplate.c8.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * property constraints
 * <p>
 * The validation constraints of a control field
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "notEmpty",
        "minLength",
        "maxLength",
        "pattern"
})
@Accessors(chain = true)
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Constraints {
    /**
     * constraints notEmpty
     * <p>
     * The control field must not be empty
     */
    @JsonProperty("notEmpty")
    @JsonPropertyDescription("The control field must not be empty")
    private Boolean notEmpty;

    /**
     * constraints minLength
     * <p>
     * The minimal length of a control field value
     */
    @JsonProperty("minLength")
    @JsonPropertyDescription("The minimal length of a control field value")
    private Double minLength;

    /**
     * constraints maxLength
     * <p>
     * The maximal length of a control field value
     */
    @JsonProperty("maxLength")
    @JsonPropertyDescription("The maximal length of a control field value")
    private Double maxLength;

    /**
     * constraints pattern
     * <p>
     * A regular expression pattern for a constraint
     */
    @JsonProperty("pattern")
    @JsonPropertyDescription("A regular expression pattern for a constraint")
    private String pattern;
}
