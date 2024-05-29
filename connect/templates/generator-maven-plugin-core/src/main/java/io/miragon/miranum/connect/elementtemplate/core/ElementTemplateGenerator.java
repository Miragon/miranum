package io.miragon.miranum.connect.elementtemplate.core;

public interface ElementTemplateGenerator {

    ElementTemplateGenerationResult generate(ElementTemplateInfo elementTemplateInfo, InputValueNamingPolicy inputValueNamingPolicy);
}
