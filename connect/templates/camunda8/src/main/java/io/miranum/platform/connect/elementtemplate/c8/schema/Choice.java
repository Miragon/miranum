package io.miranum.platform.connect.elementtemplate.c8.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * property choice
 * <p>
 * The choices for dropdown fields
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "value"
})
@Accessors(chain = true)
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Choice {
    /**
     * choice name
     * <p>
     * The name of a choice
     * (Required)
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The name of a choice")
    private String name;

    /**
     * choice value
     * <p>
     * The value of a choice
     * (Required)
     */
    @JsonProperty("value")
    @JsonPropertyDescription("The value of a choice")
    private String value;
}
