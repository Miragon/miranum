package io.miranum.platform.connect.elementtemplate.c8.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * element type
 * <p>
 * The BPMN type the element will be transformed into
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "value"
})
@Accessors(chain = true)
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ElementType {
    /**
     * element type value
     * <p>
     * The identifier of the element template
     * (Required)
     */
    @JsonProperty("value")
    private String value;
}
