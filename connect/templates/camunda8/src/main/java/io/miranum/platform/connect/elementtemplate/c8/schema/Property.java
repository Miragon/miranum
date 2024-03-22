package io.miranum.platform.connect.elementtemplate.c8.schema;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * element template property
 * <p>
 * List of properties of the element template
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "value",
        "description",
        "label",
        "type",
        "editable",
        "choices",
        "constraints",
        "group",
        "condition",
        "binding",
        "optional",
        "feel",
        "language"
})
@Accessors(chain = true)
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Property {
    /**
     * property id
     * <p>
     * The URL of an icon
     */
    @JsonProperty("id")
    @JsonPropertyDescription("Unique identifier of the property")
    private String id;

    /**
     * property value
     * <p>
     * The value of a control field
     */
    @JsonProperty("value")
    @JsonPropertyDescription("The value of a control field")
    private Object value;

    /**
     * property description
     * <p>
     * The description of a control field
     */
    @JsonProperty("description")
    @JsonPropertyDescription("The description of a control field")
    private String description;

    /**
     * property label
     * <p>
     * The label of a control field
     */
    @JsonProperty("label")
    @JsonPropertyDescription("The label of a control field")
    private String label;

    /**
     * property type
     * <p>
     * The type of a control field
     */
    @JsonProperty("type")
    @JsonPropertyDescription("The type of a control field")
    private String type;

    /**
     * property editable
     * <p>
     * Indicates whether a control field is editable or not
     */
    @JsonProperty("editable")
    @JsonPropertyDescription("Indicates whether a control field is editable or not")
    private Boolean editable;

    /**
     * property choices
     * <p>
     * The choices for dropdown fields
     */
    @JsonProperty("choices")
    @JsonPropertyDescription("The choices for dropdown fields")
    private List<Choice> choices = new ArrayList<>();

    /**
     * property constraints
     * <p>
     * The validation constraints of a control field
     */
    @JsonProperty("constraints")
    @JsonPropertyDescription("The validation constraints of a control field")
    private Constraints constraints;

    /**
     * property group
     * <p>
     * The custom group of a control field
     */
    @JsonProperty("group")
    @JsonPropertyDescription("The custom group of a control field")
    private String group;

    /**
     * property condition
     * <p>
     * Condition(s) to activate the binding
     */
    @JsonProperty("condition")
    @JsonPropertyDescription("Condition(s) to activate the binding")
    private Condition condition;

    /**
     * property binding
     * <p>
     * Specifying how the property is mapped to BPMN or Zeebe extension elements and attributes
     * (Required)
     */
    @JsonProperty("binding")
    @JsonPropertyDescription("Specifying how the property is mapped to BPMN or Zeebe extension elements and attributes")
    private Binding binding;

    /**
     * property optional
     * <p>
     * Indicates whether a property is optional. Optional bindings do not persist empty values in the underlying BPMN 2.0 XML
     */
    @JsonProperty("optional")
    @JsonPropertyDescription("Indicates whether a property is optional. Optional bindings do not persist empty values in the underlying BPMN 2.0 XML")
    private Boolean optional;

    /**
     * property feel
     * <p>
     * Indicates whether the property can be a feel expression
     */
    @JsonProperty("feel")
    @JsonPropertyDescription("Indicates whether the property can be a feel expression")
    private String feel;

    /**
     * property language
     * <p>
     * Indicates that the field is a custom language editor
     */
    @JsonProperty("language")
    @JsonPropertyDescription("Indicates that the field is a custom language editor")
    private String language;
}
