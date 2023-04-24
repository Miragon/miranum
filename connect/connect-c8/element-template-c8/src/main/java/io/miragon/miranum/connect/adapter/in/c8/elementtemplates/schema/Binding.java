package io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema;


import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * property binding
 * <p>
 * Specifying how the property is mapped to BPMN or Zeebe extension elements and attributes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "name",
        "source",
        "key"
})

public class Binding
{
    /**
     * binding type
     * <p>
     * The type of a property binding
     * (Required)
     */
    @JsonProperty("type")
    @JsonPropertyDescription("The type of a property binding")
    private Binding.Type type;

    /**
     * binding name
     * <p>
     * The name of a property binding
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The name of a property binding")
    private String name;

    /**
     * binding source
     * <p>
     * The source value of a property binding (zeebe:output)
     */
    @JsonProperty("source")
    @JsonPropertyDescription("The source value of a property binding (zeebe:output)")
    private String source;

    /**
     * binding key
     * <p>
     * The key value of a property binding (zeebe:taskHeader)
     */
    @JsonProperty("key")
    @JsonPropertyDescription("The key value of a property binding (zeebe:taskHeader)")
    private String key;

    /**
     * binding type
     * <p>
     * The type of a property binding
     * (Required)
     */
    @JsonProperty("type")
    public Binding.Type getType()
    {
        return type;
    }

    /**
     * binding type
     * <p>
     * The type of a property binding
     * (Required)
     */
    @JsonProperty("type")
    public void setType(Binding.Type type)
    {
        this.type = type;
    }

    public Binding withType(Binding.Type type)
    {
        this.type = type;
        return this;
    }

    /**
     * binding name
     * <p>
     * The name of a property binding
     */
    @JsonProperty("name")
    public String getName()
    {
        return name;
    }

    /**
     * binding name
     * <p>
     * The name of a property binding
     */
    @JsonProperty("name")
    public void setName(String name)
    {
        this.name = name;
    }

    public Binding withName(String name)
    {
        this.name = name;
        return this;
    }

    /**
     * binding source
     * <p>
     * The source value of a property binding (zeebe:output)
     */
    @JsonProperty("source")
    public String getSource()
    {
        return source;
    }

    /**
     * binding source
     * <p>
     * The source value of a property binding (zeebe:output)
     */
    @JsonProperty("source")
    public void setSource(String source)
    {
        this.source = source;
    }

    public Binding withSource(String source)
    {
        this.source = source;
        return this;
    }

    /**
     * binding key
     * <p>
     * The key value of a property binding (zeebe:taskHeader)
     */
    @JsonProperty("key")
    public String getKey()
    {
        return key;
    }

    /**
     * binding key
     * <p>
     * The key value of a property binding (zeebe:taskHeader)
     */
    @JsonProperty("key")
    public void setKey(String key)
    {
        this.key = key;
    }

    public Binding withKey(String key)
    {
        this.key = key;
        return this;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(Binding.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null) ? "<null>" : this.type));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null) ? "<null>" : this.name));
        sb.append(',');
        sb.append("source");
        sb.append('=');
        sb.append(((this.source == null) ? "<null>" : this.source));
        sb.append(',');
        sb.append("key");
        sb.append('=');
        sb.append(((this.key == null) ? "<null>" : this.key));
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
        result = ((result * 31) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((result * 31) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((result * 31) + ((this.source == null) ? 0 : this.source.hashCode()));
        result = ((result * 31) + ((this.key == null) ? 0 : this.key.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }
        if (!(other instanceof Binding rhs))
        {
            return false;
        }
        return ((((
                Objects.equals(this.type, rhs.type)) &&
                (Objects.equals(this.name, rhs.name))) &&
                (Objects.equals(this.source, rhs.source))) &&
                (Objects.equals(this.key, rhs.key)));
    }

    /**
     * property binding type
     * <p>
     * The type of the property binding
     */
    public enum Type
    {
        PROPERTY("property"),
        ZEEBE_TASKDEFINITION_TYPE("zeebe:taskdefinition:type"),
        ZEEBE_TASKDEFINITION_RETRIES("zeebe:taskdefinition:retries"),
        ZEEBE_INPUT("zeebe:input"),
        ZEEBE_OUTPUT("zeebe:output"),
        ZEEBE_PROPERTY("zeebe:property"),
        ZEEBE_TASKHEADER("zeebe:taskheader");
        private final String value;
        private final static Map<String, Type> CONSTANTS = new HashMap<String, Type>();

        static
        {
            for (Binding.Type c : values())
            {
                CONSTANTS.put(c.value, c);
            }
        }

        Type(String value)
        {
            this.value = value;
        }

        @Override
        public String toString()
        {
            return this.value;
        }

        @JsonValue
        public String value()
        {
            return this.value;
        }

        @JsonCreator
        public static Binding.Type fromValue(String value)
        {
            Binding.Type constant = CONSTANTS.get(value);
            if (constant == null)
            {
                throw new IllegalArgumentException(value);
            } else
            {
                return constant;
            }
        }
    }
}
