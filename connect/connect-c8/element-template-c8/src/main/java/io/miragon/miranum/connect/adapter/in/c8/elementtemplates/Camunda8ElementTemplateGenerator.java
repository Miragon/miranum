package io.miragon.miranum.connect.adapter.in.c8.elementtemplates;

import io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema.Binding;
import io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema.CamundaC8ElementTemplate;
import io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema.Constraints;
import io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema.Property;
import io.miragon.miranum.connect.elementtemplate.api.BPMNElementType;
import io.miragon.miranum.connect.elementtemplate.api.ElementTemplateProperty;
import io.miragon.miranum.connect.elementtemplate.api.PropertyType;
import io.miragon.miranum.connect.elementtemplate.impl.ElementTemplateGenerationResult;
import io.miragon.miranum.connect.elementtemplate.impl.ElementTemplateInfo;
import io.miragon.miranum.connect.elementtemplate.impl.GenerateElementTemplatePort;
import lombok.extern.java.Log;

import java.util.Arrays;
import static java.util.Objects.isNull;

@Log
public class Camunda8ElementTemplateGenerator implements GenerateElementTemplatePort
{

    @Override
    public ElementTemplateGenerationResult generate(ElementTemplateInfo elementTemplateInfo)
    {
        var elementTemplate = CamundaC8ElementTemplate.builder()
                .name(elementTemplateInfo.getName())
                .id(elementTemplateInfo.getId())
                .appliesTo(Arrays.stream(elementTemplateInfo.getAppliesTo()).map(BPMNElementType::getValue).toList())
                .build();

        // Add property for the topic of the external task
        var implementationTopicProperty = Property.builder()
                .label("Topic")
                .type(PropertyType.STRING.getType())
                .value(elementTemplateInfo.getType())
                .editable(false)
                .choices(null)
                .binding(Binding.builder()
                        .type(Binding.Type.ZEEBE_TASKDEFINITION_TYPE)
                        .build())
                .build();

        elementTemplate.getProperties().add(implementationTopicProperty);

        // Add properties for input parameters
        if (!isNull(elementTemplateInfo.getInputType()))
        {
            for (var field : elementTemplateInfo.getInputType().getDeclaredFields())
            {
                var type = PropertyType.getType(field.getType());
                var annotation = field.getAnnotation(ElementTemplateProperty.class);
                var property = createPropertyWithPossibleAnnotation(
                        field.getName(),
                        type,
                        "=",
                        annotation);

                var binding = Binding.builder()
                        .type(Binding.Type.ZEEBE_INPUT)
                        .name(field.getName())
                        .build();

                property.setBinding(binding);
                elementTemplate.getProperties().add(property);
            }
        }

        // Add properties for output parameters
        if (!isNull(elementTemplateInfo.getOutputType()))
        {
            for (var field : elementTemplateInfo.getOutputType().getDeclaredFields())
            {
                var type = PropertyType.getType(field.getType());
                var annotation = field.getAnnotation(ElementTemplateProperty.class);
                var property = createPropertyWithPossibleAnnotation(
                        field.getName(),
                        type,
                        field.getName() + "Result",
                        annotation);

                var binding = Binding.builder()
                        .type(Binding.Type.ZEEBE_OUTPUT)
                        .source("=" + field.getName())
                        .build();

                property.setBinding(binding);
                elementTemplate.getProperties().add(property);
            }
        }

        var json = CamundaC8ElementTemplateConverter.toJsonString(elementTemplate);
        return new ElementTemplateGenerationResult(elementTemplateInfo.getId(), elementTemplateInfo.getVersion(), json);
    }

    private Property createPropertyWithPossibleAnnotation(String label, PropertyType type, String value, ElementTemplateProperty propertyAnnotation)
    {
        var property = Property.builder()
                .label(label)
                .type(type.getType())
                .choices(null)
                .value(value)
                .build();

        if (!isNull(propertyAnnotation))
        {
            property.setLabel(propertyAnnotation.label().isEmpty() ? label : propertyAnnotation.label());
            property.setType(isNull(propertyAnnotation.type()) ? type.getType() : propertyAnnotation.type().getType());
            property.setEditable(propertyAnnotation.editable());

            var constraints = Constraints.builder().build();
            constraints.setNotEmpty(propertyAnnotation.notEmpty());
            property.setConstraints(constraints);
        }

        return property;
    }
}
