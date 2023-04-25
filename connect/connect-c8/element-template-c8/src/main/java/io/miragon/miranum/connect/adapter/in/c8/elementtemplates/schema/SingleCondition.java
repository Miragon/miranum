package io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema;

import com.fasterxml.jackson.annotation.*;

import java.util.Objects;

/**
 * property condition
 * <p>
 * Condition(s) to activate the binding
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "property",
        "expression",
})
public class SingleCondition implements Condition
{
    /**
     * condition type
     * <p>
     * The type of the condition
     */
    @JsonProperty("type")
    @JsonPropertyDescription("The type of the condition")
    private final String type = "simple";

    /**
     * condition property
     * <p>
     * The id of the property to check
     * (Required)
     */
    @JsonProperty("property")
    @JsonPropertyDescription("The id of the property to check")
    private String property;

    /**
     * condition expression
     */
    @JsonUnwrapped
    private Expression expression;

    /**
     * condition type
     * <p>
     * The type of the condition
     */
    @JsonProperty("type")
    public String getType()
    {
        return type;
    }

    /**
     * condition property
     * <p>
     * The id of the property to check
     * (Required)
     */
    @JsonProperty("property")
    public String getProperty()
    {
        return property;
    }

    /**
     * condition property
     * <p>
     * The id of the property to check
     * (Required)
     */
    @JsonProperty("property")
    public void setProperty(String property)
    {
        this.property = property;
    }

    public Condition withProperty(String property)
    {
        this.property = property;
        return this;
    }

    /**
     * condition expression
     */
    public Expression getExpression()
    {
        return expression;
    }

    /**
     * condition expression
     */
    public void setExpression(Expression expression)
    {
        this.expression = expression;
    }

    public Condition withExpression(Expression expression)
    {
        this.expression = expression;
        return this;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(Condition.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("type");
        sb.append('=');
        sb.append(this.type);
        sb.append(',');
        sb.append("property");
        sb.append('=');
        sb.append(((this.property == null) ? "<null>" : this.property));
        sb.append(',');
        sb.append("expression");
        sb.append('=');
        sb.append(((this.expression == null) ? "<null>" : this.expression));
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
        result = result * 31 + this.type.hashCode();
        result = ((result * 31) + ((this.property == null) ? 0 : this.property.hashCode()));
        result = ((result * 31) + ((this.expression == null) ? 0 : this.expression.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }
        if (!(other instanceof SingleCondition rhs))
        {
            return false;
        }
        return Objects.equals(this.property, rhs.property) && Objects.equals(this.expression, rhs.expression);
    }
}
