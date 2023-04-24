package io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

/**
 * property constraints
 * <p>
 * The validation constraints of a control field
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "notEmpty",
        "minLength",
        "maxLength",
        "pattern"
})

public class Constraints
{
    /**
     * constraints notEmpty
     * <p>
     * The control field must not be empty
     */
    @JsonProperty("notEmpty")
    @JsonPropertyDescription("The control field must not be empty")
    private Boolean notEmpty;

    /**
     * constraints minLength
     * <p>
     * The minimal length of a control field value
     */
    @JsonProperty("minLength")
    @JsonPropertyDescription("The minimal length of a control field value")
    private Double minLength;

    /**
     * constraints maxLength
     * <p>
     * The maximal length of a control field value
     */
    @JsonProperty("maxLength")
    @JsonPropertyDescription("The maximal length of a control field value")
    private Double maxLength;

    /**
     * constraints pattern
     * <p>
     * A regular expression pattern for a constraint
     */
    @JsonProperty("pattern")
    @JsonPropertyDescription("A regular expression pattern for a constraint")
    private String pattern;

    /**
     * constraints notEmpty
     * <p>
     * The control field must not be empty
     */
    @JsonProperty("notEmpty")
    public Boolean getNotEmpty()
    {
        return notEmpty;
    }

    /**
     * constraints notEmpty
     * <p>
     * The control field must not be empty
     */
    @JsonProperty("notEmpty")
    public void setNotEmpty(Boolean notEmpty)
    {
        this.notEmpty = notEmpty;
    }

    public Constraints withNotEmpty(Boolean notEmpty)
    {
        this.notEmpty = notEmpty;
        return this;
    }

    /**
     * constraints minLength
     * <p>
     * The minimal length of a control field value
     */
    @JsonProperty("minLength")
    public Double getMinLength()
    {
        return minLength;
    }

    /**
     * constraints minLength
     * <p>
     * The minimal length of a control field value
     */
    @JsonProperty("minLength")
    public void setMinLength(Double minLength)
    {
        this.minLength = minLength;
    }

    public Constraints withMinLength(Double minLength)
    {
        this.minLength = minLength;
        return this;
    }

    /**
     * constraints maxLength
     * <p>
     * The maximal length of a control field value
     */
    @JsonProperty("maxLength")
    public Double getMaxLength()
    {
        return maxLength;
    }

    /**
     * constraints maxLength
     * <p>
     * The maximal length of a control field value
     */
    @JsonProperty("maxLength")
    public void setMaxLength(Double maxLength)
    {
        this.maxLength = maxLength;
    }

    public Constraints withMaxLength(Double maxLength)
    {
        this.maxLength = maxLength;
        return this;
    }

    /**
     * constraints pattern
     * <p>
     * A regular expression pattern for a constraint
     */
    @JsonProperty("pattern")
    public String getPattern()
    {
        return pattern;
    }

    /**
     * constraints pattern
     * <p>
     * A regular expression pattern for a constraint
     */
    @JsonProperty("pattern")
    public void setPattern(String pattern)
    {
        this.pattern = pattern;
    }

    public Constraints withPattern(String pattern)
    {
        this.pattern = pattern;
        return this;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(Constraints.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.notEmpty == null) ? "<null>" : this.notEmpty));
        sb.append(',');
        sb.append("minLength");
        sb.append('=');
        sb.append(((this.minLength == null) ? "<null>" : this.minLength));
        sb.append(',');
        sb.append("maxLength");
        sb.append('=');
        sb.append(((this.maxLength == null) ? "<null>" : this.maxLength));
        sb.append(',');
        sb.append("pattern");
        sb.append('=');
        sb.append(((this.pattern == null) ? "<null>" : this.pattern));
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
        result = ((result * 31) + ((this.notEmpty == null) ? 0 : this.notEmpty.hashCode()));
        result = ((result * 31) + ((this.minLength == null) ? 0 : this.minLength.hashCode()));
        result = ((result * 31) + ((this.maxLength == null) ? 0 : this.maxLength.hashCode()));
        result = ((result * 31) + ((this.pattern == null) ? 0 : this.pattern.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }
        if (!(other instanceof Constraints rhs))
        {
            return false;
        }
        return ((((
                Objects.equals(this.notEmpty, rhs.notEmpty)) &&
                (Objects.equals(this.minLength, rhs.minLength))) &&
                (Objects.equals(this.maxLength, rhs.maxLength))) &&
                (Objects.equals(this.pattern, rhs.pattern)));
    }
}
