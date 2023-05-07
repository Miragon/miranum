package io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

/**
 * element type
 * <p>
 * The BPMN type the element will be transformed into
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "value"
})
@Builder @Getter @Setter @ToString @EqualsAndHashCode
public class ElementType
{
    /**
     * element type value
     * <p>
     * The identifier of the element template
     * (Required)
     */
    @JsonProperty("value")
    private String value;
}
