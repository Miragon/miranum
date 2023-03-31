package io.miragon.miranum.connect.elementtemplate.impl;

import io.miragon.miranum.connect.elementtemplate.api.BPMNElementType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ElementTemplateInfo {

    private String name;
    private String id;
    private int version;
    private String type;
    private BPMNElementType[] appliesTo;
    private Class<?> inputType;
    private Class<?> outputType;
}