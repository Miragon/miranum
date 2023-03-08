package io.miragon.miranum.connect.elementtemplate.impl;

import io.miragon.miranum.connect.elementtemplate.api.BPMNElementType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ElementTemplateInfo {

    private String name;
    private String id;
    private String version;
    private BPMNElementType[] appliesTo;
    private Class<?> inputType;
    private Class<?> outputType;
}