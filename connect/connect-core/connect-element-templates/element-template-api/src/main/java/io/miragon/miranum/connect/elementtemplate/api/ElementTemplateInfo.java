package io.miragon.miranum.connect.elementtemplate.api;

import io.miragon.miranum.connect.elementtemplate.api.BPMNElementType;

public class ElementTemplateInfo {

    private final String name;

    private final String id;

    private final String version;

    private final String type;

    private final BPMNElementType[] appliesTo;

    private final Class<?> inputType;

    private final Class<?> outputType;

    public ElementTemplateInfo(String name, String id, String version, String type, BPMNElementType[] appliesTo, Class<?> inputType, Class<?> outputType) {
        this.name = name;
        this.id = id;
        this.version = version;
        this.type = type;
        this.appliesTo = appliesTo;
        this.inputType = inputType;
        this.outputType = outputType;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }

    public String getType() {
        return type;
    }

    public BPMNElementType[] getAppliesTo() {
        return appliesTo;
    }

    public Class<?> getInputType() {
        return inputType;
    }

    public Class<?> getOutputType() {
        return outputType;
    }
}