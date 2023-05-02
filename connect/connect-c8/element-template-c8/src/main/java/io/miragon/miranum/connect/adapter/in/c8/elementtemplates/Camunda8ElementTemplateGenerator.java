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
import java.util.Objects;

@Log
public class Camunda8ElementTemplateGenerator implements GenerateElementTemplatePort
{

    @Override
    public ElementTemplateGenerationResult generate(ElementTemplateInfo elementTemplateInfo)
    {
        var elementTemplate = new CamundaC8ElementTemplate()
                .withName(elementTemplateInfo.getName())
                .withId(elementTemplateInfo.getId())
                .withAppliesTo(Arrays.stream(elementTemplateInfo.getAppliesTo()).map(BPMNElementType::getValue).toList());

        // Add property for the topic of the external task
        var implementationTopicProperty = new Property()
                .withLabel("Topic")
                .withType(PropertyType.STRING.getType())
                .withValue(elementTemplateInfo.getType())
                .withEditable(false)
                .withChoices(null)
                .withBinding(new Binding()
                        .withType(Binding.Type.ZEEBE_TASKDEFINITION_TYPE));
        elementTemplate.getProperties().add(implementationTopicProperty);

        // Add properties for input parameters
        if (!Objects.isNull(elementTemplateInfo.getInputType()))
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

                var binding = new Binding()
                        .withType(Binding.Type.ZEEBE_INPUT)
                        .withName(field.getName());

                property.setBinding(binding);
                elementTemplate.getProperties().add(property);
            }
        }

        // Add properties for output parameters
        if (!Objects.isNull(elementTemplateInfo.getOutputType()))
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

                var binding = new Binding()
                        .withType(Binding.Type.ZEEBE_OUTPUT)
                        .withSource("=" + field.getName());

                property.setBinding(binding);
                elementTemplate.getProperties().add(property);
            }
        }

        var json = CamundaC8ElementTemplateConverter.toJsonString(elementTemplate);
        return new ElementTemplateGenerationResult(elementTemplateInfo.getId(), elementTemplateInfo.getVersion(), json);
    }

    private Property createPropertyWithPossibleAnnotation(String label, PropertyType type, String value, ElementTemplateProperty propertyAnnotation)
    {
        var property = new Property()
                .withLabel(label)
                .withType(type.getType())
                .withChoices(null)
                .withValue(value);

        if (!Objects.isNull(propertyAnnotation))
        {
            property.setLabel(propertyAnnotation.label().isEmpty() ? label : propertyAnnotation.label());
            property.setType(Objects.isNull(propertyAnnotation.type()) ? type.getType() : propertyAnnotation.type().getType());
            property.setEditable(propertyAnnotation.editable());

            var constraints = new Constraints();
            constraints.setNotEmpty(propertyAnnotation.notEmpty());
            property.setConstraints(constraints);
        }

        return property;
    }
}
