package io.miranum.platform.connect.elementtemplate.c8.schema;

import com.fasterxml.jackson.annotation.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

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
@Accessors(chain = true)
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Binding {
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
     * property binding type
     * <p>
     * The type of the property binding
     */
    public enum Type {
        PROPERTY("property"),
        ZEEBE_TASKDEFINITION_TYPE("zeebe:taskDefinition:type"),
        ZEEBE_TASKDEFINITION_RETRIES("zeebe:taskDefinition:retries"),
        ZEEBE_INPUT("zeebe:input"),
        ZEEBE_OUTPUT("zeebe:output"),
        ZEEBE_PROPERTY("zeebe:property"),
        ZEEBE_TASKHEADER("zeebe:taskHeader");
        private final String value;
        private final static Map<String, Type> CONSTANTS = new HashMap<>();

        static {
            for (Binding.Type c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Type(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static Binding.Type fromValue(String value) {
            Binding.Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }
    }
}
