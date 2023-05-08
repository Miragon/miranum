package io.miragon.miranum.connect.adapter.in.c8.elementtemplates;

import io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema.Binding;
import io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema.CamundaC8ElementTemplate;
import io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema.Constraints;
import io.miragon.miranum.connect.adapter.in.c8.elementtemplates.schema.Property;
import io.miragon.miranum.connect.elementtemplate.api.BPMNElementType;
import io.miragon.miranum.connect.elementtemplate.api.ElementTemplateProperty;
import io.miragon.miranum.connect.elementtemplate.api.PropertyType;
import io.miragon.miranum.connect.elementtemplate.core.ElementTemplateGenerationResult;
import io.miragon.miranum.connect.elementtemplate.core.ElementTemplateGenerator;
import io.miragon.miranum.connect.elementtemplate.core.ElementTemplateInfo;
import lombok.extern.java.Log;

import java.util.List;

import static java.util.Objects.isNull;

@Log
public class Camunda8ElementTemplateGenerator implements ElementTemplateGenerator {

    @Override
    public ElementTemplateGenerationResult generate(ElementTemplateInfo elementTemplateInfo) {
        var elementTemplate = new CamundaC8ElementTemplate()
                .setName(elementTemplateInfo.getName())
                .setId(elementTemplateInfo.getId())
                .setAppliesTo(List.of(BPMNElementType.BPMN_SERVICE_TASK.getValue()));

        // Add property for the topic of the worker
        var implementationTopicProperty = new Property()
                .setLabel("Topic")
                .setType(PropertyType.STRING.getType())
                .setValue(elementTemplateInfo.getType())
                .setEditable(false)
                .setChoices(null)
                .setBinding(new Binding()
                        .setType(Binding.Type.ZEEBE_TASKDEFINITION_TYPE));

        elementTemplate.getProperties().add(implementationTopicProperty);

        // Add properties for input parameters

        if (!isNull(elementTemplateInfo.getInputType())) {
            for (var field : elementTemplateInfo.getInputType().getDeclaredFields()) {
                var type = PropertyType.getType(field.getType());
                var annotation = field.getAnnotation(ElementTemplateProperty.class);
                var property = createPropertyWithPossibleAnnotation(
                        field.getName(),
                        type,
                        "=",
                        annotation);

                var binding = new Binding()
                        .setType(Binding.Type.ZEEBE_INPUT)
                        .setName(field.getName());

                property.setBinding(binding);
                elementTemplate.getProperties().add(property);
            }
        }

        // Add properties for output parameters
        if (!isNull(elementTemplateInfo.getOutputType())) {
            for (var field : elementTemplateInfo.getOutputType().getDeclaredFields()) {
                var type = PropertyType.getType(field.getType());
                var annotation = field.getAnnotation(ElementTemplateProperty.class);
                var property = createPropertyWithPossibleAnnotation(
                        field.getName(),
                        type,
                        field.getName() + "Result",
                        annotation);

                var binding = new Binding()
                        .setType(Binding.Type.ZEEBE_OUTPUT)
                        .setSource("=" + field.getName());

                property.setBinding(binding);
                elementTemplate.getProperties().add(property);
            }
        }

        var json = CamundaC8ElementTemplateConverter.toJsonString(elementTemplate);
        return new ElementTemplateGenerationResult(elementTemplateInfo.getId(), elementTemplateInfo.getVersion(), json);
    }

    private Property createPropertyWithPossibleAnnotation(String label, PropertyType type, String value, ElementTemplateProperty propertyAnnotation) {
        var property = new Property()
                .setLabel(label)
                .setType(type.getType())
                .setChoices(null)
                .setValue(value);

        if (!isNull(propertyAnnotation)) {
            property.setLabel(propertyAnnotation.label().isEmpty() ? label : propertyAnnotation.label());
            property.setType(isNull(propertyAnnotation.type()) ? type.getType() : propertyAnnotation.type().getType());
            property.setEditable(propertyAnnotation.editable());

            var constraints = new Constraints();
            constraints.setNotEmpty(propertyAnnotation.notEmpty());
            property.setConstraints(constraints);
        }

        return property;
    }
}
