package io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * condition equals
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "equals"
})
@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode
public class Equals implements Expression
{
    /**
     * condition equals
     */
    @JsonProperty("equals")
    private Object equals;
}
