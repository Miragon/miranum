package io.miragon.miranum.connect.elementtemplate.impl;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ElementTemplateGenerator {

    private final GenerateElementTemplatePort generateElementTemplatePort;

    public void generate(GenerateElementTemplatesCommand generateElementTemplatesCommand) {
        generateElementTemplatesCommand.getElementTemplateInfos().forEach(generateElementTemplatePort::generate);
    }
}