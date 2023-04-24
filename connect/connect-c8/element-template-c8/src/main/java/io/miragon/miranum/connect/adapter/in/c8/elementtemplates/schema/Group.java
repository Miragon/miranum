package io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

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

public class Group
{
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

    /**
     * group id
     * <p>
     * The id of the custom group
     * (Required)
     */
    @JsonProperty("id")
    public String getId()
    {
        return id;
    }

    /**
     * group id
     * <p>
     * The id of the custom group
     * (Required)
     */
    @JsonProperty("id")
    public void setId(String id)
    {
        this.id = id;
    }

    public Group withId(String id)
    {
        this.id = id;
        return this;
    }

    /**
     * group label
     * <p>
     * The label of the custom group
     * (Required)
     */
    @JsonProperty("label")
    public String getLabel()
    {
        return label;
    }

    /**
     * group label
     * <p>
     * The label of the custom group
     * (Required)
     */
    @JsonProperty("label")
    public void setLabel(String label)
    {
        this.label = label;
    }

    public Group withLabel(String label)
    {
        this.label = label;
        return this;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(Group.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null) ? "<null>" : this.id));
        sb.append(',');
        sb.append("label");
        sb.append('=');
        sb.append(((this.label == null) ? "<null>" : this.label));
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
        result = ((result * 31) + ((this.id == null) ? 0 : this.id.hashCode()));
        result = ((result * 31) + ((this.label == null) ? 0 : this.label.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }
        if (!(other instanceof Group rhs))
        {
            return false;
        }
        return ((
                Objects.equals(this.id, rhs.id)) &&
                (Objects.equals(this.label, rhs.label)));
    }
}
