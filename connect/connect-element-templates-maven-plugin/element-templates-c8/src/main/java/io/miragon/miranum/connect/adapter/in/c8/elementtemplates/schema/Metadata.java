package io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * element template metadata
 * <p>
 * Some custom properties for further configuration
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({

})
@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode
public class Metadata
{
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<>();
}
