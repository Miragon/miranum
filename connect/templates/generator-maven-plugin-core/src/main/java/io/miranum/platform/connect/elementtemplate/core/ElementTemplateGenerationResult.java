package io.miranum.platform.connect.elementtemplate.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ElementTemplateGenerationResult {

    private String name;

    private String version;

    private String json;

    private TargetPlatform targetPlatform;

    public String getFileName() {
        return name + "-" + version + ".json";
    }
}