package io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * property condition
 * <p>
 * Condition(s) to activate the binding
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "allMatch"
})
public class MultipleConditions implements Condition
{
    /**
     * condition allMatch
     * (Required)
     */
    @JsonProperty("allMatch")
    private List<SingleCondition> allMatch = new ArrayList<>();

    /**
     * condition allMatch
     * (Required)
     */
    @JsonProperty("allMatch")
    public List<SingleCondition> getAllMatch()
    {
        return allMatch;
    }

    /**
     * condition allMatch
     * (Required)
     */
    @JsonProperty("allMatch")
    public void setAllMatch(List<SingleCondition> allMatch)
    {
        this.allMatch = allMatch;
    }

    public Condition withAllMatch(List<SingleCondition> allMatch)
    {
        this.allMatch = allMatch;
        return this;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(Condition.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("allMatch");
        sb.append('=');
        sb.append(((this.allMatch == null) ? "<null>" : this.allMatch));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',')
        {
            sb.setCharAt((sb.length() - 1), ']');
        } else
        {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode()
    {
        int result = 1;
        result = ((result * 31) + ((this.allMatch == null) ? 0 : this.allMatch.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }
        if (!(other instanceof MultipleConditions rhs))
        {
            return false;
        }
        return Objects.equals(this.allMatch, rhs.allMatch);

    }
}
