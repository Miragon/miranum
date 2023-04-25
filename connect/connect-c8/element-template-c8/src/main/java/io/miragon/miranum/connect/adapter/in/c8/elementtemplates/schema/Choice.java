package io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

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
public class Choice
{
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

    /**
     * choice name
     * <p>
     * The name of a choice
     */
    @JsonProperty("name")
    public String getName()
    {
        return name;
    }

    /**
     * choice name
     * <p>
     * The name of a choice
     */
    @JsonProperty("name")
    public void setName(String name)
    {
        this.name = name;
    }

    public Choice withName(String name)
    {
        this.name = name;
        return this;
    }

    /**
     * choice value
     * <p>
     * The value of a choice
     */
    @JsonProperty("value")
    public String getValue()
    {
        return value;
    }

    /**
     * choice value
     * <p>
     * The value of a choice
     */
    @JsonProperty("value")
    public void setValue(String value)
    {
        this.value = value;
    }

    public Choice withValue(String value)
    {
        this.value = value;
        return this;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(Choice.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null) ? "<null>" : this.name));
        sb.append(',');
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
        result = ((result * 31) + ((this.name == null) ? 0 : this.name.hashCode()));
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
        if (!(other instanceof Choice rhs))
        {
            return false;
        }
        return ((
                Objects.equals(this.name, rhs.name)) &&
                (Objects.equals(this.value, rhs.value)));
    }
}
