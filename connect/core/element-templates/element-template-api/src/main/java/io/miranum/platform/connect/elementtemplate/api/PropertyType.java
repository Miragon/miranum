package io.miranum.platform.connect.elementtemplate.api;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.extern.java.Log;

@Log
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

    public static PropertyType getType(Class<?> type) {
        if (type != String.class) {
            log.warning(String.format("Unsupported type \"%s\". PropertyType String is assumed.", type.getName()));
        }
        return PropertyType.STRING;
    }
}