package io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.util.Map;

/**
 * element template metadata
 * <p>
 * Some custom properties for further configuration
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({

})
@Builder @Getter @Setter @ToString @EqualsAndHashCode
public class Metadata
{
    @JsonIgnore
    private Map<String, Object> additionalProperties;
}
