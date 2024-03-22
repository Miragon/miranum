package io.miranum.platform.connect.elementtemplate.c8.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

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
@Accessors(chain = true)
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CamundaC8ElementTemplate {
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
    @Singular
    private List<Property> properties = new ArrayList<>();

    /**
     * element template icon
     * <p>
     * Custom icon to be shown on the element
     */
    @JsonProperty("icon")
    @JsonPropertyDescription("Custom icon to be shown on the element")
    private Icon icon;
}
