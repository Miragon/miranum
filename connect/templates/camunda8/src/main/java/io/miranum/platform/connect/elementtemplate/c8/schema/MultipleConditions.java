package io.miranum.platform.connect.elementtemplate.c8.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * property condition
 * <p>
 * Condition(s) to activate the binding
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "allMatch"
})
@Accessors(chain = true)
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class MultipleConditions implements Condition {
    /**
     * condition allMatch
     * (Required)
     */
    @JsonProperty("allMatch")
    private List<SingleCondition> allMatch = new ArrayList<>();
}
