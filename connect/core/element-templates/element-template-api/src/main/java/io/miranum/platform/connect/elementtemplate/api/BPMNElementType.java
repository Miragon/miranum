package io.miranum.platform.connect.elementtemplate.api;

public enum BPMNElementType {
    BPMN_SERVICE_TASK("bpmn:ServiceTask"),
    BPMN_SEND_TASK("bpmn:SendTask"),
    BPMN_INTERMEDIATE_THROW_EVENT("bpmn:IntermediateThrowEvent");

    private final String value;

    BPMNElementType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}