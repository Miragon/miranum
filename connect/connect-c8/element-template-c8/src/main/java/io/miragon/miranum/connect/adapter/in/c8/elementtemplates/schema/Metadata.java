package io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema;

import com.fasterxml.jackson.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * element template metadata
 * <p>
 * Some custom properties for further configuration
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({

})

public class Metadata
{
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties()
    {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value)
    {
        this.additionalProperties.put(name, value);
    }

    public Metadata withAdditionalProperty(String name, Object value)
    {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(Metadata.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null) ? "<null>" : this.additionalProperties));
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
        result = ((result * 31) + ((this.additionalProperties == null) ? 0 : this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }
        if (!(other instanceof Metadata rhs))
        {
            return false;
        }
        return (Objects.equals(this.additionalProperties, rhs.additionalProperties));
    }

}
