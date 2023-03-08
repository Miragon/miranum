package io.miragon.miranum.connect.adapter.in.c7.elementtemplates;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

// This class is temporary and should be replaced by a generated class from the json schema
@Getter
@Setter
@JsonPropertyOrder({ "$schema", "name", "id", "appliesTo", "properties" })
public class Camunda7ElementTemplate {

    @JsonProperty("$schema")
    private String schema;
    private String name;
    private String id;
    private List<String> appliesTo;
    private List<Property> properties;

    public Camunda7ElementTemplate() {
        this.properties = new ArrayList<>();
    }
}

@Getter
@Setter
class Property {
    private String label;
    private String type;
    private Binding binding;
    private Constraints constraints;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String value;

    public Property() {
        this.constraints = new Constraints();
        this.constraints.setNotEmpty(true);
    }
}

@Getter
@Setter
class Binding {
    private String type;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String source;
}

@Getter
@Setter
class Constraints {
    private boolean notEmpty;
}