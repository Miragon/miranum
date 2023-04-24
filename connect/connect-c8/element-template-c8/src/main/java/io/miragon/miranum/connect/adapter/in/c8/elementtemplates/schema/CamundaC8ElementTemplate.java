package io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Element Template Schema
 * <p>
 * An element template configuration
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "id",
        "description",
        "version",
        "isDefault",
        "appliesTo",
        "elementType",
        "metadata",
        "entriesVisible",
        "groups",
        "documentationRef",
        "properties",
        "icon"
})

public class CamundaC8ElementTemplate
{
    /**
     * element template name
     * <p>
     * The name of the element template
     * (Required)
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The name of the element template")
    private String name;

    /**
     * element template id
     * <p>
     * The identifier of the element template
     * (Required)
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The identifier of the element template")
    private String id;

    /**
     * element template description
     * <p>
     * The description of the element template
     */
    @JsonProperty("description")
    @JsonPropertyDescription("The description of the element template")
    private String description;

    /**
     * element template version
     * <p>
     * Optional version of the template. If you add a version to a template it will be considered unique based on its ID
     * and version. Two templates can have the same ID if their version is different
     */
    @JsonProperty("version")
    @JsonPropertyDescription("Optional version of the template. If you add a version to a template it will be " +
            "considered unique based on its ID and version. Two templates can have the same ID if their version is different")
    private Integer version;

    /**
     * element template isDefault
     * <p>
     * Indicates whether the element template is a default template
     */
    @JsonProperty("isDefault")
    @JsonPropertyDescription("Indicates whether the element template is a default template")
    private Boolean isDefault;

    /**
     * element template appliesTo
     * <p>
     * List of BPMN types the template can be applied to
     * (Required)
     */
    @JsonProperty("appliesTo")
    @JsonPropertyDescription("List of BPMN types the template can be applied to")
    private List<String> appliesTo = new ArrayList<>();

    /**
     * element template elementType
     * <p>
     * The BPMN type the element will be transformed into
     */
    @JsonProperty("elementType")
    @JsonPropertyDescription("The BPMN type the element will be transformed into")
    private ElementType elementType;

    /**
     * element template metadata
     * <p>
     * Some custom properties for further configuration
     */
    @JsonProperty("metadata")
    @JsonPropertyDescription("Some custom properties for further configuration")
    private Metadata metadata;

    /**
     * element template entriesVisible
     * <p>
     * Select whether non-template entries are visible in the properties panel
     */
    @JsonProperty("entriesVisible")
    @JsonPropertyDescription("Select whether non-template entries are visible in the properties panel")
    private Boolean entriesVisible;

    /**
     * element template groups
     * <p>
     * Custom fields can be ordered together via groups
     */
    @JsonProperty("groups")
    @JsonPropertyDescription("Custom fields can be ordered together via groups")
    private List<Group> groups = new ArrayList<>();

    /**
     * element template documentationRef
     */
    @JsonProperty("documentationRef")
    private String documentationRef;

    /**
     * element template properties
     * <p>
     * List of properties of the element template
     * (Required)
     */
    @JsonProperty("properties")
    @JsonPropertyDescription("List of properties of the element template")
    private List<Property> properties = new ArrayList<>();

    /**
     * element template icon
     * <p>
     * Custom icon to be shown on the element
     */
    @JsonProperty("icon")
    @JsonPropertyDescription("Custom icon to be shown on the element")
    private Icon icon;

    /**
     * element template name
     * <p>
     * The name of the element template
     * (Required)
     */
    @JsonProperty("name")
    public String getName()
    {
        return name;
    }

    /**
     * element template name
     * <p>
     * The name of the element template
     * (Required)
     */
    @JsonProperty("name")
    public void setName(String name)
    {
        this.name = name;
    }

    public CamundaC8ElementTemplate withName(String name)
    {
        this.name = name;
        return this;
    }

    /**
     * element template id
     * <p>
     * The identifier of the element template
     * (Required)
     */
    @JsonProperty("id")
    public String getId()
    {
        return id;
    }

    /**
     * element template id
     * <p>
     * The identifier of the element template
     * (Required)
     */
    @JsonProperty("id")
    public void setId(String id)
    {
        this.id = id;
    }

    public CamundaC8ElementTemplate withId(String id)
    {
        this.id = id;
        return this;
    }

    /**
     * element template description
     * <p>
     * The description of the element template
     */
    @JsonProperty("description")
    public String getDescription()
    {
        return description;
    }

    /**
     * element template description
     * <p>
     * The description of the element template
     */
    @JsonProperty("description")
    public void setDescription(String description)
    {
        this.description = description;
    }

    public CamundaC8ElementTemplate withDescription(String description)
    {
        this.description = description;
        return this;
    }

    /**
     * element template version
     * <p>
     * Optional version of the template. If you add a version to a template it will be considered unique based on its ID
     * and version. Two templates can have the same ID if their version is different
     */
    @JsonProperty("version")
    public Integer getVersion()
    {
        return version;
    }

    /**
     * element template version
     * <p>
     * Optional version of the template. If you add a version to a template it will be considered unique based on its ID
     * and version. Two templates can have the same ID if their version is different
     */
    @JsonProperty("version")
    public void setVersion(Integer version)
    {
        this.version = version;
    }

    public CamundaC8ElementTemplate withVersion(Integer version)
    {
        this.version = version;
        return this;
    }

    /**
     * element template isDefault
     * <p>
     * Indicates whether the element template is a default template
     */
    @JsonProperty("isDefault")
    public Boolean getIsDefault()
    {
        return isDefault;
    }

    /**
     * element template isDefault
     * <p>
     * Indicates whether the element template is a default template
     */
    @JsonProperty("isDefault")
    public void setIsDefault(Boolean aDefault)
    {
        isDefault = aDefault;
    }

    public CamundaC8ElementTemplate withIsDefault(Boolean isDefault)
    {
        this.isDefault = isDefault;
        return this;
    }

    /**
     * element template appliesTo
     * <p>
     * List of BPMN types the template can be applied to
     * (Required)
     */
    @JsonProperty("appliesTo")
    public List<String> getAppliesTo()
    {
        return appliesTo;
    }

    public CamundaC8ElementTemplate withAppliesTo(List<String> appliesTo)
    {
        this.appliesTo = appliesTo;
        return this;
    }

    /**
     * element template elementType
     * <p>
     * The BPMN type the element will be transformed into
     */
    @JsonProperty("elementType")
    public ElementType getElementType()
    {
        return elementType;
    }

    /**
     * element template elementType
     * <p>
     * The BPMN type the element will be transformed into
     */
    @JsonProperty("elementType")
    public void setElementType(ElementType elementType)
    {
        this.elementType = elementType;
    }

    public CamundaC8ElementTemplate withElementType(ElementType elementType)
    {
        this.elementType = elementType;
        return this;
    }

    /**
     * element template metadata
     * <p>
     * Some custom properties for further configuration
     */
    @JsonProperty("metadata")
    public Metadata getMetadata()
    {
        return metadata;
    }

    /**
     * element template metadata
     * <p>
     * Some custom properties for further configuration
     */
    @JsonProperty("metadata")
    public void setMetadata(Metadata metadata)
    {
        this.metadata = metadata;
    }

    public CamundaC8ElementTemplate withMetadata(Metadata metadata)
    {
        this.metadata = metadata;
        return this;
    }

    /**
     * element template entriesVisible
     * <p>
     * Select whether non-template entries are visible in the properties panel
     */
    @JsonProperty("entriesVisible")
    public Boolean getEntriesVisible()
    {
        return entriesVisible;
    }

    /**
     * element template entriesVisible
     * <p>
     * Select whether non-template entries are visible in the properties panel
     */
    @JsonProperty("entriesVisible")
    public void setEntriesVisible(Boolean entriesVisible)
    {
        this.entriesVisible = entriesVisible;
    }

    public CamundaC8ElementTemplate withEntriesVisible(Boolean entriesVisible)
    {
        this.entriesVisible = entriesVisible;
        return this;
    }

    /**
     * element template groups
     * <p>
     * Custom fields can be ordered together via groups
     */
    @JsonProperty("groups")
    public List<Group> getGroups()
    {
        return groups;
    }

    public CamundaC8ElementTemplate withGroups(List<Group> groups)
    {
        this.groups = groups;
        return this;
    }

    /**
     * element template documentationRef
     */
    @JsonProperty("documentationRef")
    public String getDocumentationRef()
    {
        return documentationRef;
    }

    /**
     * element template documentationRef
     */
    @JsonProperty("documentationRef")
    public void setDocumentationRef(String documentationRef)
    {
        this.documentationRef = documentationRef;
    }

    public CamundaC8ElementTemplate withDocumentationRef(String documentationRef)
    {
        this.documentationRef = documentationRef;
        return this;
    }

    /**
     * element template properties
     * <p>
     * List of properties of the element template
     * (Required)
     */
    @JsonProperty("properties")
    public List<Property> getProperties()
    {
        return properties;
    }

    /**
     * element template properties
     * <p>
     * List of properties of the element template
     * (Required)
     */
    @JsonProperty("properties")
    public void setProperties(List<Property> properties)
    {
        this.properties = properties;
    }

    public CamundaC8ElementTemplate withProperties(List<Property> properties)
    {
        this.properties = properties;
        return this;
    }

    /**
     * element template icon
     * <p>
     * Custom icon to be shown on the element
     */
    @JsonProperty("icon")
    public Icon getIcon()
    {
        return icon;
    }

    /**
     * element template icon
     * <p>
     * Custom icon to be shown on the element
     */
    @JsonProperty("icon")
    public void setIcon(Icon icon)
    {
        this.icon = icon;
    }

    public CamundaC8ElementTemplate withIcon(Icon icon)
    {
        this.icon = icon;
        return this;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(CamundaC8ElementTemplate.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null) ? "<null>" : this.name));
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null) ? "<null>" : this.id));
        sb.append(',');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null) ? "<null>" : this.description));
        sb.append(',');
        sb.append("version");
        sb.append('=');
        sb.append(((this.version == null) ? "<null>" : this.version));
        sb.append(',');
        sb.append("isDefault");
        sb.append('=');
        sb.append(((this.isDefault == null) ? "<null>" : this.isDefault));
        sb.append(',');
        sb.append("appliesTo");
        sb.append('=');
        sb.append(((this.appliesTo == null) ? "<null>" : this.appliesTo));
        sb.append(',');
        sb.append("elementTypes");
        sb.append('=');
        sb.append(((this.elementType == null) ? "<null>" : this.elementType));
        sb.append(',');
        sb.append("metadata");
        sb.append('=');
        sb.append(((this.metadata == null) ? "<null>" : this.metadata));
        sb.append(',');
        sb.append("entriesVisible");
        sb.append('=');
        sb.append(((this.entriesVisible == null) ? "<null>" : this.entriesVisible));
        sb.append(',');
        sb.append("groups");
        sb.append('=');
        sb.append(((this.groups == null) ? "<null>" : this.groups));
        sb.append(',');
        sb.append("documentationRef");
        sb.append('=');
        sb.append(((this.documentationRef == null) ? "<null>" : this.documentationRef));
        sb.append(',');
        sb.append("properties");
        sb.append('=');
        sb.append(((this.properties == null) ? "<null>" : this.properties));
        sb.append(',');
        sb.append("icon");
        sb.append('=');
        sb.append(((this.icon == null) ? "<null>" : this.icon));
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
        result = ((result * 31) + ((this.isDefault == null) ? 0 : this.isDefault.hashCode()));
        result = ((result * 31) + ((this.metadata == null) ? 0 : this.metadata.hashCode()));
        result = ((result * 31) + ((this.entriesVisible == null) ? 0 : this.entriesVisible.hashCode()));
        result = ((result * 31) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((result * 31) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((result * 31) + ((this.appliesTo == null) ? 0 : this.appliesTo.hashCode()));
        result = ((result * 31) + ((this.id == null) ? 0 : this.id.hashCode()));
        result = ((result * 31) + ((this.groups == null) ? 0 : this.groups.hashCode()));
        result = ((result * 31) + ((this.version == null) ? 0 : this.version.hashCode()));
        result = ((result * 31) + ((this.properties == null) ? 0 : this.properties.hashCode()));
        result = ((result * 31) + ((this.icon == null) ? 0 : this.icon.hashCode()));
        result = ((result * 31) + ((this.elementType == null) ? 0 : this.elementType.hashCode()));
        result = ((result * 31) + ((this.documentationRef == null) ? 0 : this.documentationRef.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }
        if (!(other instanceof CamundaC8ElementTemplate rhs))
        {
            return false;
        }
        return (((((((((((((
                Objects.equals(this.isDefault, rhs.isDefault)) &&
                (Objects.equals(this.metadata, rhs.metadata))) &&
                (Objects.equals(this.entriesVisible, rhs.entriesVisible))) &&
                (Objects.equals(this.name, rhs.name))) &&
                (Objects.equals(this.description, rhs.description))) &&
                (Objects.equals(this.appliesTo, rhs.appliesTo))) &&
                (Objects.equals(this.id, rhs.id))) &&
                (Objects.equals(this.groups, rhs.groups))) &&
                (Objects.equals(this.version, rhs.version))) &&
                (Objects.equals(this.properties, rhs.properties))) &&
                (Objects.equals(this.icon, rhs.icon))) &&
                (Objects.equals(this.elementType, rhs.elementType))) &&
                (Objects.equals(this.documentationRef, rhs.documentationRef)));
    }
}
