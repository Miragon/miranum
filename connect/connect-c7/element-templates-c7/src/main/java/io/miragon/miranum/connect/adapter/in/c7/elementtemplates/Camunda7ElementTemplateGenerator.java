package io.miragon.miranum.connect.adapter.in.c7.elementtemplates;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.miragon.miranum.connect.elementtemplate.impl.ElementTemplateGenerationResult;
import io.miragon.miranum.connect.elementtemplate.impl.ElementTemplateInfo;
import io.miragon.miranum.connect.elementtemplate.impl.GenerateElementTemplatePort;

import java.util.List;

public class Camunda7ElementTemplateGenerator implements GenerateElementTemplatePort {

    private static final String SCHEMA = "https://unpkg.com/@camunda/element-templates-json-schema@0.10.0/resources/schema.json";
    private static final String INPUT_PARAMETER = "camunda:inputParameter";
    private static final String OUTPUT_PARAMETER = "camunda:outputParameter";

    @Override
    public ElementTemplateGenerationResult generate(ElementTemplateInfo elementTemplateInfo) {
        var elementTemplate = new Camunda7ElementTemplate();
        elementTemplate.setName(elementTemplateInfo.getName());
        elementTemplate.setId(elementTemplateInfo.getId());
        elementTemplate.setAppliesTo(List.of(elementTemplateInfo.getAppliesTo().split(";")));
        elementTemplate.setSchema(SCHEMA);

        for (var field : elementTemplateInfo.getInputType().getDeclaredFields()) {
            var property = new Property();
            property.setLabel(field.getName());
            property.setType(field.getType().getSimpleName());
            var binding = new Binding();
            binding.setType(INPUT_PARAMETER);
            binding.setName(field.getName());
            property.setBinding(binding);
            elementTemplate.getProperties().add(property);
        }

        for (var field : elementTemplateInfo.getOutputType().getDeclaredFields()) {
            var properties = new Property();
            properties.setLabel(field.getName());
            properties.setType(field.getType().getSimpleName());
            properties.setValue(field.getName());
            var binding = new Binding();
            binding.setType(OUTPUT_PARAMETER);
            binding.setSource(String.format("${%s}", field.getName()));
            properties.setBinding(binding);
            elementTemplate.getProperties().add(properties);
        }

        var objectMapper = new ObjectMapper();
        var json = "";
        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(elementTemplate);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new ElementTemplateGenerationResult(json);
    }
}