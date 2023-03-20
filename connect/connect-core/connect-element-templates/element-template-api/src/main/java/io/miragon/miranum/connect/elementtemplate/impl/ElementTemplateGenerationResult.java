package io.miragon.miranum.connect.elementtemplate.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ElementTemplateGenerationResult {

    private String name;
    private double version;
    private String jsonString;
}