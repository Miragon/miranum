package io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * constraints pattern
 * <p>
 * A regular expression pattern for a constraint
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "value",
        "message"
})
public class PatternObject implements Pattern
{
    /**
     * pattern value
     * <p>
     * The regular expression of a pattern
     */
    @JsonProperty("value")
    @JsonPropertyDescription("The regular expression of a pattern")
    private String value;

    /**
     * pattern message
     * <p>
     * The validation message of a pattern
     */
    @JsonProperty("message")
    @JsonPropertyDescription("The validation message of a pattern")
    private String message;

    /**
     * pattern value
     * <p>
     * The regular expression of a pattern
     */
    @JsonProperty("value")
    public String getValue()
    {
        return value;
    }

    /**
     * pattern value
     * <p>
     * The regular expression of a pattern
     */
    @JsonProperty("value")
    public void setValue(String value)
    {
        this.value = value;
    }

    public PatternObject withValue(String value)
    {
        this.value = value;
        return this;
    }

    /**
     * pattern message
     * <p>
     * The validation message of a pattern
     */
    @JsonProperty("message")
    public String getMessage()
    {
        return message;
    }

    /**
     * pattern message
     * <p>
     * The validation message of a pattern
     */
    @JsonProperty("message")
    public void setMessage(String message)
    {
        this.message = message;
    }

    public PatternObject withMessage(String message)
    {
        this.message = message;
        return this;
    }
}
