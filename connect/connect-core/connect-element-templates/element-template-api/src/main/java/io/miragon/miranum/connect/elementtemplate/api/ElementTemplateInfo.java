package io.miragon.miranum.connect.elementtemplate.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ElementTemplateInfo {

    private final String name;

    private final String id;

    private final String version;

    private final String type;

    private final BPMNElementType[] appliesTo;

    private final Class<?> inputType;

    private final Class<?> outputType;
}