package io.miragon.miranum.connect.elementtemplate.impl;

public class ElementTemplateGenerator {

    private GenerateElementTemplatePort generateElementTemplatePort;

    public void generate(GenerateElementTemplatesCommand generateElementTemplatesCommand) {
        generateElementTemplatesCommand.getElementTemplateInfos().forEach(generateElementTemplatePort::generate);
    }
}