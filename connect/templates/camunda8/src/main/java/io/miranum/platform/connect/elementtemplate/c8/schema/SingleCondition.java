package io.miranum.platform.connect.elementtemplate.c8.schema;

import com.fasterxml.jackson.annotation.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * property condition
 * <p>
 * Condition(s) to activate the binding
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "property",
        "expression",
})
@Accessors(chain = true)
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SingleCondition implements Condition {
    /**
     * condition type
     * <p>
     * The type of the condition
     */
    @JsonProperty("type")
    @JsonPropertyDescription("The type of the condition")
    private final String type = "simple";

    /**
     * condition property
     * <p>
     * The id of the property to check
     * (Required)
     */
    @JsonProperty("property")
    @JsonPropertyDescription("The id of the property to check")
    private String property;

    /**
     * condition expression
     */
    @JsonUnwrapped
    private Expression expression;
}
