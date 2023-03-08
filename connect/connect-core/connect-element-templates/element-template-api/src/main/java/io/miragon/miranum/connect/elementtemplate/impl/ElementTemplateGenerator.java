package io.miragon.miranum.connect.elementtemplate.impl;

import lombok.AllArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@AllArgsConstructor
public class ElementTemplateGenerator {

    private final GenerateElementTemplatePort generateElementTemplatePort;

    public void generate(GenerateElementTemplatesCommand generateElementTemplatesCommand) {
        generateElementTemplatesCommand.getElementTemplateInfos().forEach(
                elementTemplateInfo -> {
                    var json = generateElementTemplatePort.generate(elementTemplateInfo);

                    var filename = elementTemplateInfo.getId() + "-" + elementTemplateInfo.getVersion() + ".json";
                    saveElementTemplateToFile(filename, json);
                }
        );
    }

    private void saveElementTemplateToFile(String filename, ElementTemplateGenerationResult json) {
        var dir = "element-templates";
        try {
            var classLoader = getClass().getClassLoader();
            var dirPath = Path.of(Objects.requireNonNull(classLoader.getResource(".")).getFile(), dir);
            if (!dirPath.toFile().exists()) {
                dirPath.toFile().mkdir();
            }
            var filePath = Path.of(String.valueOf(dirPath), filename);
            var file = new File(String.valueOf(filePath));
            var success = file.createNewFile();
            Files.writeString(filePath, json.getJsonString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}