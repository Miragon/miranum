package io.miragon.miranum.connect.elementtemplate.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@AllArgsConstructor
@Slf4j
public class ElementTemplatesGenerator {

    private final GenerateElementTemplatePort generateElementTemplatePort;

    public void generate(GenerateElementTemplatesCommand generateElementTemplatesCommand) {
        generateElementTemplatesCommand.getElementTemplateInfos().forEach(
                elementTemplateInfo -> {
                    var json = generateElementTemplatePort.generate(elementTemplateInfo);

                    var filename = elementTemplateInfo.getId() + "-" + elementTemplateInfo.getVersion() + ".json";
                    saveElementTemplateToFile(filename, json);

                    log.info("Generated element template: {}", filename);
                }
        );
    }

    private void saveElementTemplateToFile(String filename, ElementTemplateGenerationResult json) {
        var dir = "element-templates";
        var targetPath = getClass().getClassLoader().getResource(".").getFile();
        var elementTemplate = new File(Path.of(targetPath, dir, filename).toUri());
        elementTemplate.getParentFile().mkdirs();
        try {
            elementTemplate.createNewFile();
            Files.writeString(elementTemplate.toPath(), json.getJsonString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}