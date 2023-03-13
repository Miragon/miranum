package io.miragon.miranum.connect.elementtemplate.api;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PropertyType {
    STRING("String"),
    TEXT("Text"),
    HIDDEN("Hidden");

    // Currently not supported:
    //  - DROPDOWN("Dropdown")
    //  - BOOLEAN("Boolean"): not supported as type for camunda:inputParameter and camunda:outputParameter

    private final String type;

    PropertyType(String type) {
        this.type = type;
    }

    @JsonValue
    public String getType() {
        return type;
    }
}