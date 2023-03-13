package io.miragon.miranum.connect.adapter.in.c7.elementtemplates;

import io.miragon.miranum.connect.c7.elementtemplates.gen.Binding;
import io.miragon.miranum.connect.c7.elementtemplates.gen.CamundaC7ElementTemplate;
import io.miragon.miranum.connect.c7.elementtemplates.gen.Constraints;
import io.miragon.miranum.connect.c7.elementtemplates.gen.Property;
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
        var elementTemplate = new CamundaC7ElementTemplate()
                .withName(elementTemplateInfo.getName())
                .withId(elementTemplateInfo.getId())
                .withAppliesTo(Arrays.stream(elementTemplateInfo.getAppliesTo()).map(BPMNElementType::getValue).toList())
                .withVersion(elementTemplateInfo.getVersion());

        // Add external task property
        var implementationProperty = new Property()
                .withLabel("Implementation Type")
                .withType(PropertyType.STRING.getType())
                .withValue("external")
                .withEditable(false)
                .withBinding(new Binding()
                        .withType(Binding.Type.PROPERTY)
                        .withName("camunda:type"));
        elementTemplate.getProperties().add(implementationProperty);

        // Add property for the topic of the external task
        var implementationTopicProperty = new Property()
                .withLabel("Topic")
                .withType(PropertyType.STRING.getType())
                .withValue(elementTemplateInfo.getType())
                .withBinding(new Binding()
                        .withType(Binding.Type.PROPERTY)
                        .withName("camunda:topic"));
        elementTemplate.getProperties().add(implementationTopicProperty);

        // Add properties for input parameters
        if (!Objects.isNull(elementTemplateInfo.getInputType())) {
            for (var field : elementTemplateInfo.getInputType().getDeclaredFields()) {
                var type = getType(field.getType());
                var annotation = field.getAnnotation(ElementTemplateProperty.class);
                var property = createProperty(
                        field.getName(),
                        type,
                        "",
                        annotation);

                var binding = new Binding()
                        .withType(Binding.Type.CAMUNDA_INPUT_PARAMETER)
                        .withName(field.getName());

                property.setBinding(binding);
                elementTemplate.getProperties().add(property);
            }
        }

        // Add properties for output parameters
        if (!Objects.isNull(elementTemplateInfo.getOutputType())) {
            for (var field : elementTemplateInfo.getOutputType().getDeclaredFields()) {
                var type = getType(field.getType());
                var annotation = field.getAnnotation(ElementTemplateProperty.class);
                var property = createProperty(
                        field.getName(),
                        type,
                        field.getName() + "Result",
                        annotation);

                var binding = new Binding()
                        .withType(Binding.Type.CAMUNDA_OUTPUT_PARAMETER)
                        .withSource(field.getName());

                property.setBinding(binding);
                elementTemplate.getProperties().add(property);
            }
        }

        var json = CamundaC7ElementTemplateConverter.toJsonString(elementTemplate);
        return new ElementTemplateGenerationResult(json);
    }

    private Property createProperty(String label, PropertyType type, String value, ElementTemplateProperty propertyAnnotation) {
        var property = new Property()
                .withLabel(label)
                .withType(type.getType())
                .withValue(value);

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