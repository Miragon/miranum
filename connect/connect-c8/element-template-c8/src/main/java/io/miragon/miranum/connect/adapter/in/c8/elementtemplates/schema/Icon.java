package io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * element template icon
 * <p>
 * Custom icon to be shown on the element
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "contents"
})
@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode
public class Icon
{

    /**
     * icon contents
     * <p>
     * The URL of an icon
     * (Required)
     */
    @JsonProperty("contents")
    @JsonPropertyDescription("The URL of an icon")
    private String contents;
}
