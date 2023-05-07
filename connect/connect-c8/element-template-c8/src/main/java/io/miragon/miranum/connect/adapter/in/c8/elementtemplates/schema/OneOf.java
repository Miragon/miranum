package io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * condition equals
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "oneOf"
})
@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode
public class OneOf implements Expression
{
    /**
     * condition oneOf
     */
    @JsonProperty("oneOf")
    private List<Object> oneOf = new ArrayList<>();
}
