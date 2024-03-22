package io.miranum.platform.connect.elementtemplate.c8.schema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Metadata {
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<>();
}
