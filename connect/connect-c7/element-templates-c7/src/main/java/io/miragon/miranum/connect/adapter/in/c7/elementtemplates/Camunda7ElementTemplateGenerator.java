package io.miragon.miranum.connect.adapter.in.c7.elementtemplates;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.miragon.miranum.connect.adapter.in.c7.elementtemplates.model.Binding;
import io.miragon.miranum.connect.adapter.in.c7.elementtemplates.model.Camunda7ElementTemplate;
import io.miragon.miranum.connect.adapter.in.c7.elementtemplates.model.Constraints;
import io.miragon.miranum.connect.adapter.in.c7.elementtemplates.model.Property;
import io.miragon.miranum.connect.elementtemplate.api.BPMNElementType;
import io.miragon.miranum.connect.elementtemplate.api.ElementTemplateProperty;
import io.miragon.miranum.connect.elementtemplate.impl.ElementTemplateGenerationResult;
import io.miragon.miranum.connect.elementtemplate.impl.ElementTemplateInfo;
import io.miragon.miranum.connect.elementtemplate.impl.GenerateElementTemplatePort;

import java.util.Arrays;
import java.util.Objects;

public class Camunda7ElementTemplateGenerator implements GenerateElementTemplatePort {

    private static final String SCHEMA = "https://unpkg.com/@camunda/element-templates-json-schema@0.10.0/resources/schema.json";
    private static final String INPUT_PARAMETER = "camunda:inputParameter";
    private static final String OUTPUT_PARAMETER = "camunda:outputParameter";
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

        var implementationProperty = createProperty("Implementation Type", "String", IMPLEMENTATION_TYPE_VALUE);
        implementationProperty.setEditable(false);
        var implementationBinding = createBinding("property", "", IMPLEMENTATION_TYPE);
        implementationProperty.setBinding(implementationBinding);
        elementTemplate.getProperties().add(implementationProperty);

        var implementationTopicProperty = createProperty("Topic", "String", elementTemplateInfo.getType());
        var implementationTopicBinding = createBinding("property", "", IMPLEMENTATION_TOPIC);
        implementationTopicProperty.setBinding(implementationTopicBinding);
        elementTemplate.getProperties().add(implementationTopicProperty);

        for (var field : elementTemplateInfo.getInputType().getDeclaredFields()) {
            var property = createProperty(field.getName(),
                    field.getType().getSimpleName(),
                    "",
                    field.getAnnotation(ElementTemplateProperty.class));

            var binding = createBinding(INPUT_PARAMETER, "", field.getName());

            property.setBinding(binding);
            elementTemplate.getProperties().add(property);
        }

        for (var field : elementTemplateInfo.getOutputType().getDeclaredFields()) {

            var property = createProperty(field.getName(),
                    field.getType().getSimpleName(),
                    field.getName() + "Result",
                    field.getAnnotation(ElementTemplateProperty.class));

            var binding = createBinding(OUTPUT_PARAMETER, String.format("${%s}", field.getName()), "");

            property.setBinding(binding);
            elementTemplate.getProperties().add(property);
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

    private Property createProperty(String label, String type, String value, ElementTemplateProperty propertyAnnotation) {
        var property = createProperty(label, type, value);

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

    private Property createProperty(String label, String type, String value) {
        var property = new Property();

        property.setLabel(label);
        property.setType(type);
        property.setValue(value);

        return property;
    }

    private Binding createBinding(String type, String source, String name) {
        var binding = new Binding();
        binding.setType(type);
        binding.setSource(source);
        binding.setName(name);
        return binding;
    }
}