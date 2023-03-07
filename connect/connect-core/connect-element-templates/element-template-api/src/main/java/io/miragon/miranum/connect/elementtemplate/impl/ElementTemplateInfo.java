package io.miragon.miranum.connect.elementtemplate.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ElementTemplateInfo {

    private String name;
    private String id;
    private int version;
    private String appliesTo;
    private Class<?> inputType;
    private Class<?> outputType;
}