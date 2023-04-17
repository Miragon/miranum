package io.miragon.miranum.connect.elementtemplate.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ElementTemplateGenerationResult {

    private String name;
    private String version;
    private String json;
}