package io.miragon.miranum.connect.elementtemplate.api;

public class ElementTemplateGenerationResult {

    private String name;
    private String version;
    private String json;

    public ElementTemplateGenerationResult(String name, String version, String json) {
        this.name = name;
        this.version = version;
        this.json = json;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getJson() {
        return json;
    }
}