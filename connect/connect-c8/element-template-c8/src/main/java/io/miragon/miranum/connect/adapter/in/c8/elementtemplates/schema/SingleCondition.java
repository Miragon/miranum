package io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

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
@Builder @Getter @Setter @ToString @EqualsAndHashCode
public class SingleCondition implements Condition
{
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
