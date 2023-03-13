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
        var resourcePath = Objects.requireNonNull(getClass().getClassLoader().getResource(".")).getFile();
        var elementTemplateDirPath = Path.of("..", "..", "src", "main", "resources", dir);
        try {
            var dirPath = Path.of(resourcePath, elementTemplateDirPath.toString());
            dirPath.toFile().mkdirs();
            var filePath = Path.of(String.valueOf(dirPath), filename);
            var file = new File(String.valueOf(filePath));
            file.createNewFile();
            Files.writeString(filePath, json.getJsonString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}