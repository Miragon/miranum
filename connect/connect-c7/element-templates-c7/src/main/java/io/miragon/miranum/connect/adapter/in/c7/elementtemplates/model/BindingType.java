package io.miragon.miranum.connect.adapter.in.c7.elementtemplates.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BindingType {

    INPUT_PARAMETER("camunda:inputParameter"),
    OUTPUT_PARAMETER("camunda:outputParameter"),
    PROPERTY("property");

    private final String type;

    BindingType(String type) {
        this.type = type;
    }

    @JsonValue
    public String getType() {
        return type;
    }
}