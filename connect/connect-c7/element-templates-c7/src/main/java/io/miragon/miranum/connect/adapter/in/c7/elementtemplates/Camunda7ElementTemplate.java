package io.miragon.miranum.connect.adapter.in.c7.elementtemplates;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

// This class is temporary and should be replaced by a generated class from the json schema
@Getter
@Setter
public class Camunda7ElementTemplate {
    private String $schema;
    private String name;
    private String id;
    private List<String> appliesTo;
    private List<Property> properties;

    public void set$schema(String $schema) {
        this.$schema = $schema;
    }

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

    public Property() {
        this.constraints = new Constraints();
        this.constraints.setNotEmpty(true);
    }
}

@Getter
@Setter
class Binding {
    private String type;
    private String name;
}

@Getter
@Setter
class Constraints {
    private boolean notEmpty;
}