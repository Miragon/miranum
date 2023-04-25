package io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * condition equals
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "oneOf"
})
public class OneOf implements Expression
{
    /**
     * condition oneOf
     */
    @JsonProperty("oneOf")
    private List<Object> oneOf = new ArrayList<>();

    /**
     * condition oneOf
     */
    @JsonProperty("oneOf")
    public List<Object> getOneOf()
    {
        return oneOf;
    }

    /**
     * condition oneOf
     */
    @JsonProperty("oneOf")
    public void setOneOf(List<Object> oneOf)
    {
        this.oneOf = oneOf;
    }

    public OneOf withOneOf(List<Object> oneOf)
    {
        this.oneOf = oneOf;
        return this;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(Expression.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("oneOf");
        sb.append('=');
        sb.append(this.oneOf);
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
        result = ((result * 31) + ((this.oneOf == null) ? 0 : this.oneOf.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }
        if (!(other instanceof OneOf rhs))
        {
            return false;
        }
        return Objects.equals(this.oneOf, rhs.oneOf);
    }
}
