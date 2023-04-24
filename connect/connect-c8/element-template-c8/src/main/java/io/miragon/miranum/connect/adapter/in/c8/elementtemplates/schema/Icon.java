package io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;


/**
 * element template icon
 * <p>
 * Custom icon to be shown on the element
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "contents"
})

public class Icon
{

    /**
     * icon contents
     * <p>
     * The URL of an icon
     * (Required)
     */
    @JsonProperty("contents")
    @JsonPropertyDescription("The URL of an icon")
    private String contents;

    /**
     * icon contents
     * <p>
     * The URL of an icon
     * (Required)
     */
    @JsonProperty("contents")
    public String getContents()
    {
        return contents;
    }

    /**
     * icon contents
     * <p>
     * The URL of an icon
     * (Required)
     */
    @JsonProperty("contents")
    public void setContents(String contents)
    {
        this.contents = contents;
    }

    public Icon withContents(String contents)
    {
        this.contents = contents;
        return this;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(Icon.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("contents");
        sb.append('=');
        sb.append(((this.contents == null) ? "<null>" : this.contents));
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
        result = ((result * 31) + ((this.contents == null) ? 0 : this.contents.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }
        if (!(other instanceof Icon rhs))
        {
            return false;
        }
        return Objects.equals(this.contents, rhs.contents);
    }
}
