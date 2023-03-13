package io.miragon.miranum.connect.adapter.in.c7.elementtemplates;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.miragon.miranum.connect.adapter.in.c7.elementtemplates.model.*;
import io.miragon.miranum.connect.elementtemplate.api.BPMNElementType;
import io.miragon.miranum.connect.elementtemplate.api.ElementTemplateProperty;
import io.miragon.miranum.connect.elementtemplate.impl.ElementTemplateGenerationResult;
import io.miragon.miranum.connect.elementtemplate.impl.ElementTemplateInfo;
import io.miragon.miranum.connect.elementtemplate.impl.GenerateElementTemplatePort;

import java.util.Arrays;
import java.util.Objects;

public class Camunda7ElementTemplateGenerator implements GenerateElementTemplatePort {

    private static final String SCHEMA = "https://unpkg.com/@camunda/element-templates-json-schema@0.10.0/resources/schema.json";
    private static final String IMPLEMENTATION_TYPE = "camunda:type";
    private static final String IMPLEMENTATION_TYPE_VALUE = "external";
    private static final String IMPLEMENTATION_TOPIC = "camunda:topic";

    @Override
    public ElementTemplateGenerationResult generate(ElementTemplateInfo elementTemplateInfo) {
        var elementTemplate = new Camunda7ElementTemplate();
        elementTemplate.setName(elementTemplateInfo.getName());
        elementTemplate.setId(elementTemplateInfo.getId());
        elementTemplate.setAppliesTo(Arrays.stream(elementTemplateInfo.getAppliesTo()).map(BPMNElementType::getValue).toList());
        elementTemplate.setSchema(SCHEMA);

        var implementationProperty = new Property("Implementation Type", "String", IMPLEMENTATION_TYPE_VALUE);
        implementationProperty.setEditable(false);
        var implementationBinding = new Binding(BindingType.PROPERTY, "", IMPLEMENTATION_TYPE);
        implementationProperty.setBinding(implementationBinding);
        elementTemplate.getProperties().add(implementationProperty);

        var implementationTopicProperty = new Property("Topic", "String", elementTemplateInfo.getType());
        var implementationTopicBinding = new Binding(BindingType.PROPERTY, "", IMPLEMENTATION_TOPIC);
        implementationTopicProperty.setBinding(implementationTopicBinding);
        elementTemplate.getProperties().add(implementationTopicProperty);

        if (!Objects.isNull(elementTemplateInfo.getInputType())) {
            for (var field : elementTemplateInfo.getInputType().getDeclaredFields()) {
                var property = createPropertyWithAnnotation(field.getName(),
                        field.getType().getSimpleName(),
                        "",
                        field.getAnnotation(ElementTemplateProperty.class));

                var binding = new Binding(BindingType.INPUT_PARAMETER, "", field.getName());

                property.setBinding(binding);
                elementTemplate.getProperties().add(property);
            }
        }

        if (!Objects.isNull(elementTemplateInfo.getOutputType())) {
            for (var field : elementTemplateInfo.getOutputType().getDeclaredFields()) {
                var property = createPropertyWithAnnotation(field.getName(),
                        field.getType().getSimpleName(),
                        field.getName() + "Result",
                        field.getAnnotation(ElementTemplateProperty.class));

                var binding = new Binding(BindingType.OUTPUT_PARAMETER, field.getName(), "");

                property.setBinding(binding);
                elementTemplate.getProperties().add(property);
            }
        }

        return new ElementTemplateGenerationResult(convertToJsonString(elementTemplate));
    }

    private String convertToJsonString(Camunda7ElementTemplate elementTemplate) {
        var objectMapper = new ObjectMapper();
        var json = "";
        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(elementTemplate);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    private Property createPropertyWithAnnotation(String label, String type, String value, ElementTemplateProperty propertyAnnotation) {
        var property = new Property(label, type, value);

        if (!Objects.isNull(propertyAnnotation)) {
            property.setLabel(propertyAnnotation.label().isEmpty() ? label : propertyAnnotation.label());
            property.setType(propertyAnnotation.type().isEmpty() ? type : propertyAnnotation.type());
            property.setEditable(propertyAnnotation.editable());

            var constraints = new Constraints();
            constraints.setNotEmpty(propertyAnnotation.notEmpty());
            property.setConstraints(constraints);
        }

        return property;
    }
}