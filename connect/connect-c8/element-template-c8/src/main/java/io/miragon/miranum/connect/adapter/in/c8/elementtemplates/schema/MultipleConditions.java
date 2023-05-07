package io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

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
@Builder @Getter @Setter @ToString @EqualsAndHashCode
public class MultipleConditions implements Condition
{
    /**
     * condition allMatch
     * (Required)
     */
    @JsonProperty("allMatch")
    private List<SingleCondition> allMatch;
}
