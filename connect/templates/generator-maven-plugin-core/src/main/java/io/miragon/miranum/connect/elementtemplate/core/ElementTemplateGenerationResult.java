package io.miragon.miranum.connect.elementtemplate.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ElementTemplateGenerationResult {

    private String name;

    private int version;

    private String json;

    private TargetPlatform targetPlatform;

    public String getFileName() {
        var v = version > 0 ? "-v%s".formatted(version) : "";
        return  "%s%s.json".formatted(name, v);
    }
}
