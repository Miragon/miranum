package io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

/**
 * condition equals
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "equals"
})
public class Equals implements Expression
{
    /**
     * condition equals
     */
    @JsonProperty("equals")
    private Object equals;

    /**
     * condition equals
     */
    @JsonProperty("equals")
    public Object getEquals()
    {
        return equals;
    }

    /**
     * condition equals
     */
    @JsonProperty("equals")
    public void setEquals(Object equals)
    {
        this.equals = equals;
    }

    public Equals withEquals(Object equals)
    {
        this.equals = equals;
        return this;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(Expression.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("equals");
        sb.append('=');
        sb.append(this.equals);
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
        result = ((result * 31) + ((this.equals == null) ? 0 : this.equals.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }
        if (!(other instanceof Equals rhs))
        {
            return false;
        }
        return Objects.equals(this.equals, rhs.equals);
    }
}
