package io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
public class Property
{
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

    /**
     * property id
     * <p>
     * The URL of an icon
     */
    @JsonProperty("id")
    public String getId()
    {
        return id;
    }

    /**
     * property id
     * <p>
     * The URL of an icon
     */
    @JsonProperty("id")
    public void setId(String id)
    {
        this.id = id;
    }

    public Property withId(String id)
    {
        this.id = id;
        return this;
    }

    /**
     * property value
     * <p>
     * The value of a control field
     */
    @JsonProperty("value")
    public Object getValue()
    {
        return value;
    }

    /**
     * property value
     * <p>
     * The value of a control field
     */
    @JsonProperty("value")
    public void setValue(Object value)
    {
        this.value = value;
    }

    public Property withValue(Object value)
    {
        this.value = value;
        return this;
    }

    /**
     * property description
     * <p>
     * The description of a control field
     */
    @JsonProperty("description")
    public String getDescription()
    {
        return description;
    }

    /**
     * property description
     * <p>
     * The description of a control field
     */
    @JsonProperty("description")
    public void setDescription(String description)
    {
        this.description = description;
    }

    public Property withDescription(String description)
    {
        this.description = description;
        return this;
    }

    /**
     * property label
     * <p>
     * The label of a control field
     */
    @JsonProperty("label")
    public String getLabel()
    {
        return label;
    }

    /**
     * property label
     * <p>
     * The label of a control field
     */
    @JsonProperty("label")
    public void setLabel(String label)
    {
        this.label = label;
    }

    public Property withLabel(String label)
    {
        this.label = label;
        return this;
    }

    /**
     * property type
     * <p>
     * The type of a control field
     */
    @JsonProperty("type")
    public String getType()
    {
        return type;
    }

    /**
     * property type
     * <p>
     * The type of a control field
     */
    @JsonProperty("type")
    public void setType(String type)
    {
        this.type = type;
    }

    public Property withType(String type)
    {
        this.type = type;
        return this;
    }

    /**
     * property editable
     * <p>
     * Indicates whether a control field is editable or not
     */
    @JsonProperty("editable")
    public Boolean getEditable()
    {
        return editable;
    }

    /**
     * property editable
     * <p>
     * Indicates whether a control field is editable or not
     */
    @JsonProperty("editable")
    public void setEditable(Boolean editable)
    {
        this.editable = editable;
    }

    public Property withEditable(Boolean editable)
    {
        this.editable = editable;
        return this;
    }

    /**
     * property choices
     * <p>
     * The choices for dropdown fields
     */
    @JsonProperty("choices")
    public List<Choice> getChoices()
    {
        return choices;
    }

    /**
     * property choices
     * <p>
     * The choices for dropdown fields
     */
    @JsonProperty("choices")
    public void setChoices(List<Choice> choices)
    {
        this.choices = choices;
    }

    public Property withChoices(List<Choice> choices)
    {
        this.choices = choices;
        return this;
    }

    /**
     * property constraints
     * <p>
     * The validation constraints of a control field
     */
    @JsonProperty("constraints")
    public Constraints getConstraints()
    {
        return constraints;
    }

    /**
     * property constraints
     * <p>
     * The validation constraints of a control field
     */
    @JsonProperty("constraints")
    public void setConstraints(Constraints constraints)
    {
        this.constraints = constraints;
    }

    public Property withContraints(Constraints constraints)
    {
        this.constraints = constraints;
        return this;
    }

    /**
     * property group
     * <p>
     * The custom group of a control field
     */
    @JsonProperty("group")
    public String getGroup()
    {
        return group;
    }

    /**
     * property group
     * <p>
     * The custom group of a control field
     */
    @JsonProperty("group")
    public void setGroup(String group)
    {
        this.group = group;
    }

    public Property withGroup(String group)
    {
        this.group = group;
        return this;
    }

    /**
     * property condition
     * <p>
     * Condition(s) to activate the binding
     */
    @JsonProperty("condition")
    public Condition getCondition()
    {
        return condition;
    }

    /**
     * property condition
     * <p>
     * Condition(s) to activate the binding
     */
    @JsonProperty("condition")
    public void setCondition(Condition condition)
    {
        this.condition = condition;
    }

    public Property withCondition(Condition condition)
    {
        this.condition = condition;
        return this;
    }

    /**
     * property binding
     * <p>
     * Specifying how the property is mapped to BPMN or Zeebe extension elements and attributes
     * (Required)
     */
    @JsonProperty("binding")
    public Binding getBinding()
    {
        return binding;
    }

    /**
     * property binding
     * <p>
     * Specifying how the property is mapped to BPMN or Zeebe extension elements and attributes
     * (Required)
     */
    @JsonProperty("binding")
    public void setBinding(Binding binding)
    {
        this.binding = binding;
    }

    public Property withBinding(Binding binding)
    {
        this.binding = binding;
        return this;
    }

    /**
     * property optional
     * <p>
     * Indicates whether a property is optional. Optional bindings do not persist empty values in the underlying BPMN 2.0 XML
     */
    @JsonProperty("optional")
    public Boolean getOptional()
    {
        return optional;
    }

    /**
     * property optional
     * <p>
     * Indicates whether a property is optional. Optional bindings do not persist empty values in the underlying BPMN 2.0 XML
     */
    @JsonProperty("optional")
    public void setOptional(Boolean optional)
    {
        this.optional = optional;
    }

    public Property withOptional(Boolean optional)
    {
        this.optional = optional;
        return this;
    }

    /**
     * property feel
     * <p>
     * Indicates whether the property can be a feel expression
     */
    @JsonProperty("feel")
    public String getFeel()
    {
        return feel;
    }

    /**
     * property feel
     * <p>
     * Indicates whether the property can be a feel expression
     */
    @JsonProperty("feel")
    public void setFeel(String feel)
    {
        this.feel = feel;
    }

    public Property withFeel(String feel)
    {
        this.feel = feel;
        return this;
    }

    /**
     * property language
     * <p>
     * Indicates that the field is a custom language editor
     */
    @JsonProperty("language")
    public String getLanguage()
    {
        return language;
    }

    /**
     * property language
     * <p>
     * Indicates that the field is a custom language editor
     */
    @JsonProperty("language")
    public void setLanguage(String language)
    {
        this.language = language;
    }

    public Property withLanguage(String language)
    {
        this.language = language;
        return this;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(Property.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null) ? "<null>" : this.id));
        sb.append(',');
        sb.append("value");
        sb.append('=');
        sb.append(((this.value == null) ? "<null>" : this.value));
        sb.append(',');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null) ? "<null>" : this.description));
        sb.append(',');
        sb.append("label");
        sb.append('=');
        sb.append(((this.label == null) ? "<null>" : this.label));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null) ? "<null>" : this.type));
        sb.append(',');
        sb.append("editable");
        sb.append('=');
        sb.append(((this.editable == null) ? "<null>" : this.editable));
        sb.append(',');
        sb.append("choices");
        sb.append('=');
        sb.append(((this.choices == null) ? "<null>" : this.choices));
        sb.append(',');
        sb.append("constraints");
        sb.append('=');
        sb.append(((this.constraints == null) ? "<null>" : this.constraints));
        sb.append(',');
        sb.append("group");
        sb.append('=');
        sb.append(((this.group == null) ? "<null>" : this.group));
        sb.append(',');
        sb.append("condition");
        sb.append('=');
        sb.append(((this.condition == null) ? "<null>" : this.condition));
        sb.append(',');
        sb.append("binding");
        sb.append('=');
        sb.append(((this.binding == null) ? "<null>" : this.binding));
        sb.append(',');
        sb.append("optional");
        sb.append('=');
        sb.append(((this.optional == null) ? "<null>" : this.optional));
        sb.append(',');
        sb.append("feel");
        sb.append('=');
        sb.append(((this.feel == null) ? "<null>" : this.feel));
        sb.append(',');
        sb.append("language");
        sb.append('=');
        sb.append(((this.language == null) ? "<null>" : this.language));
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
        result = ((result * 31) + ((this.value == null) ? 0 : this.value.hashCode()));
        result = ((result * 31) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((result * 31) + ((this.label == null) ? 0 : this.label.hashCode()));
        result = ((result * 31) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((result * 31) + ((this.editable == null) ? 0 : this.editable.hashCode()));
        result = ((result * 31) + ((this.choices == null) ? 0 : this.choices.hashCode()));
        result = ((result * 31) + ((this.constraints == null) ? 0 : this.constraints.hashCode()));
        result = ((result * 31) + ((this.group == null) ? 0 : this.group.hashCode()));
        result = ((result * 31) + ((this.condition == null) ? 0 : this.condition.hashCode()));
        result = ((result * 31) + ((this.binding == null) ? 0 : this.binding.hashCode()));
        result = ((result * 31) + ((this.optional == null) ? 0 : this.optional.hashCode()));
        result = ((result * 31) + ((this.feel == null) ? 0 : this.feel.hashCode()));
        result = ((result * 31) + ((this.language == null) ? 0 : this.language.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }
        if (!(other instanceof Property rhs))
        {
            return false;
        }
        return ((((((((((((((
                Objects.equals(this.id, rhs.id)) &&
                (Objects.equals(this.value, rhs.value))) &&
                (Objects.equals(this.description, rhs.description))) &&
                (Objects.equals(this.label, rhs.label))) &&
                (Objects.equals(this.type, rhs.type))) &&
                (Objects.equals(this.editable, rhs.editable))) &&
                (Objects.equals(this.choices, rhs.choices))) &&
                (Objects.equals(this.constraints, rhs.constraints))) &&
                (Objects.equals(this.group, rhs.group))) &&
                (Objects.equals(this.condition, rhs.condition))) &&
                (Objects.equals(this.binding, rhs.binding))) &&
                (Objects.equals(this.optional, rhs.optional))) &&
                (Objects.equals(this.feel, rhs.feel))) &&
                (Objects.equals(this.language, rhs.language)));
    }
}
