package io.miragon.miranum.connect.adapter.in.c7.elementtemplates.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.miragon.miranum.connect.elementtemplate.api.PropertyType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public
class Property {
    private String label;
    private PropertyType type;
    private Binding binding;
    private Constraints constraints;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String value;
    private boolean editable;

    public Property() {
        this.editable = true;
        this.constraints = new Constraints();
        this.constraints.setNotEmpty(true);
    }
    public Property(String label, PropertyType type, String value) {
        this();
        this.label = label;
        this.type = type;
        this.value = value;
    }
}