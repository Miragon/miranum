package io.miragon.miranum.connect.adapter.in.c7.elementtemplates.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public
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