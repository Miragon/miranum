package io.miragon.miranum.connect.adapter.in.c7.elementtemplates.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public
class Binding {
    private String type;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String source;

    public Binding(String type, String source, String name) {
        this.type = type;
        this.source = source;
        this.name = name;
    }
}