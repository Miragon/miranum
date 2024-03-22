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
 * element template groups
 * <p>
 * Custom fields can be ordered together via groups
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "label"
})
@Accessors(chain = true)
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Group {
    /**
     * group id
     * <p>
     * The id of the custom group
     * (Required)
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The id of the custom group")
    private String id;

    /**
     * group label
     * <p>
     * The label of the custom group
     * (Required)
     */
    @JsonProperty("label")
    @JsonPropertyDescription("The label of the custom group")
    private String label;
}
