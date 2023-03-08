package io.miragon.miranum.connect.elementtemplate.api;

public enum BPMNElementType {
    BPMN_SERVICE_TASK("bpmn:ServiceTask");

    private final String value;

    BPMNElementType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}