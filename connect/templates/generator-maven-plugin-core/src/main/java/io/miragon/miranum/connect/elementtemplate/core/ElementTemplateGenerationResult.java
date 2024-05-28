package io.miragon.miranum.connect.elementtemplate.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ElementTemplateGenerationResult {

    private String name;

    private int version;

    private String json;

    public String getFileName() {
        return  "%s-v%s.json".formatted(name, version);
    }
}
