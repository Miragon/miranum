package io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;


/**
 * element type
 * <p>
 * The BPMN type the element will be transformed into
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "value"
})

public class ElementType
{
    /**
     * element type value
     * <p>
     * The identifier of the element template
     * (Required)
     */
    @JsonProperty("value")
    private String value;

    /**
     * element type value
     * <p>
     * The identifier of the element template
     * (Required)
     */
    @JsonProperty("value")
    public String getValue()
    {
        return value;
    }

    /**
     * element type value
     * <p>
     * The identifier of the element template
     * (Required)
     */
    @JsonProperty("value")
    public void setValue(String value)
    {
        this.value = value;
    }

    public ElementType withValue(String value)
    {
        this.value = value;
        return this;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(ElementType.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("value");
        sb.append('=');
        sb.append(((this.value == null) ? "<null>" : this.value));
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
        result = ((result * 31) + ((this.value == null) ? 0 : this.value.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }
        if (!(other instanceof ElementType rhs))
        {
            return false;
        }
        return Objects.equals(this.value, rhs.value);
    }
}
