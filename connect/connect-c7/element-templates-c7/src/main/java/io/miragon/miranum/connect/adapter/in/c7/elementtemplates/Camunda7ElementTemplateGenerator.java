package io.miragon.miranum.connect.adapter.in.c7.elementtemplates;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.miragon.miranum.connect.c7.elementtemplates.gen.Constraints;
import io.miragon.miranum.connect.c7.elementtemplates.gen.ElementTemplatesJsonSchema010;
import io.miragon.miranum.connect.c7.elementtemplates.gen.Property;
import io.miragon.miranum.connect.c7.elementtemplates.gen.Binding;
import io.miragon.miranum.connect.elementtemplate.api.BPMNElementType;
import io.miragon.miranum.connect.elementtemplate.api.ElementTemplateProperty;
import io.miragon.miranum.connect.elementtemplate.api.PropertyType;
import io.miragon.miranum.connect.elementtemplate.impl.ElementTemplateGenerationResult;
import io.miragon.miranum.connect.elementtemplate.impl.ElementTemplateInfo;
import io.miragon.miranum.connect.elementtemplate.impl.GenerateElementTemplatePort;
import lombok.extern.java.Log;

import java.util.Arrays;
import java.util.Objects;

@Log
public class Camunda7ElementTemplateGenerator implements GenerateElementTemplatePort {


    @Override
    public ElementTemplateGenerationResult generate(ElementTemplateInfo elementTemplateInfo) {
        var elementTemplate = new ElementTemplatesJsonSchema010();
        elementTemplate.setName(elementTemplateInfo.getName());
        elementTemplate.setId(elementTemplateInfo.getId());
        elementTemplate.setAppliesTo(Arrays.stream(elementTemplateInfo.getAppliesTo()).map(BPMNElementType::getValue).toList());

        // Add external task property
        var implementationProperty = new Property();
        implementationProperty.setLabel("Implementation Type");
        implementationProperty.setType(PropertyType.STRING.getType());
        implementationProperty.setValue("external");
        implementationProperty.setEditable(false);

        var implementationBinding = new Binding();
        implementationBinding.setType(Binding.Type.PROPERTY);
        implementationBinding.setName("camunda:type");
        implementationProperty.setBinding(implementationBinding);
        elementTemplate.getProperties().add(implementationProperty);

        // Add property for the topic of the external task
        var implementationTopicProperty = new Property();
        implementationTopicProperty.setLabel("Topic");
        implementationTopicProperty.setType(PropertyType.STRING.getType());
        implementationTopicProperty.setValue(elementTemplateInfo.getType());

        var implementationTopicBinding = new Binding();
        implementationTopicBinding.setType(Binding.Type.PROPERTY);
        implementationTopicBinding.setName("camunda:topic");
        implementationTopicProperty.setBinding(implementationTopicBinding);
        elementTemplate.getProperties().add(implementationTopicProperty);

        // Add properties for input parameters
        if (!Objects.isNull(elementTemplateInfo.getInputType())) {
            for (var field : elementTemplateInfo.getInputType().getDeclaredFields()) {
                var type = getType(field.getType());
                var property = createPropertyWithAnnotation(
                        field.getName(),
                        type,
                        "",
                        field.getAnnotation(ElementTemplateProperty.class));

                var binding = new Binding();
                binding.setType(Binding.Type.CAMUNDA_INPUT_PARAMETER);
                binding.setName(field.getName());

                property.setBinding(binding);
                elementTemplate.getProperties().add(property);
            }
        }

        // Add properties for output parameters
        if (!Objects.isNull(elementTemplateInfo.getOutputType())) {
            for (var field : elementTemplateInfo.getOutputType().getDeclaredFields()) {
                var type = getType(field.getType());
                var property = createPropertyWithAnnotation(
                        field.getName(),
                        type,
                        field.getName() + "Result",
                        field.getAnnotation(ElementTemplateProperty.class));

                var binding = new Binding();
                binding.setType(Binding.Type.CAMUNDA_OUTPUT_PARAMETER);
                binding.setSource(field.getName());

                property.setBinding(binding);
                elementTemplate.getProperties().add(property);
            }
        }

        return new ElementTemplateGenerationResult(convertToJsonString(elementTemplate));
    }

    private String convertToJsonString(ElementTemplatesJsonSchema010 elementTemplate) {
        var objectMapper = new ObjectMapper();
        var json = "";
        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(elementTemplate);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not generate json string!", e);
        }
        return json;
    }

    private Property createPropertyWithAnnotation(String label, PropertyType type, String value, ElementTemplateProperty propertyAnnotation) {
        var property = new Property();
        property.setLabel(label);
        property.setType(type.getType());
        property.setValue(value);

        if (!Objects.isNull(propertyAnnotation)) {
            property.setLabel(propertyAnnotation.label().isEmpty() ? label : propertyAnnotation.label());
            property.setType(Objects.isNull(propertyAnnotation.type()) ? type.getType() : propertyAnnotation.type().getType());
            property.setEditable(propertyAnnotation.editable());

            var constraints = new Constraints();
            constraints.setNotEmpty(propertyAnnotation.notEmpty());
            property.setConstraints(constraints);
        }

        return property;
    }

    private PropertyType getType(Class<?> type) {
        if (type != String.class) {
            log.warning(String.format("Unsupported type \"%s\". PropertyType String is assumed.", type.getName()));
        }
        return PropertyType.STRING;
    }
}