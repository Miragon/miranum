package io.miragon.miranum.connect.elementtemplate.c8;

import io.miragon.miranum.connect.elementtemplate.api.BPMNElementType;
import io.miragon.miranum.connect.elementtemplate.api.PropertyType;
import io.miragon.miranum.connect.elementtemplate.c8.schema.Binding;
import io.miragon.miranum.connect.elementtemplate.c8.schema.CamundaC8ElementTemplate;
import io.miragon.miranum.connect.elementtemplate.c8.schema.Constraints;
import io.miragon.miranum.connect.elementtemplate.c8.schema.Property;
import io.miragon.miranum.connect.elementtemplate.core.*;

import java.util.List;

public class Camunda8ElementTemplateGenerator implements ElementTemplateGenerator {

    @Override
    public ElementTemplateGenerationResult generate(ElementTemplateInfo elementTemplateInfo, InputValueNamingPolicy inputValueNamingPolicy) {
        var elementTemplate = new CamundaC8ElementTemplate()
                .setName(elementTemplateInfo.getName())
                .setId(elementTemplateInfo.getId())
                .setAppliesTo(List.of(BPMNElementType.BPMN_SERVICE_TASK.getValue()));

        if (elementTemplateInfo.getVersion() > 0) {
            elementTemplate.setVersion(elementTemplateInfo.getVersion());
        }

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
        for (var inputProperty : elementTemplateInfo.getInputProperties()) {
            var property = createInputParameterProp(inputProperty, inputValueNamingPolicy);
            elementTemplate.getProperties().add(property);
        }

        // Add properties for output parameters
        for (var outputProperties : elementTemplateInfo.getOutputProperties()) {
            var property = createOutputParameterProp(outputProperties);
            elementTemplate.getProperties().add(property);
        }

        var json = CamundaC8ElementTemplateConverter.toJsonString(elementTemplate);
        return new ElementTemplateGenerationResult(
                elementTemplateInfo.getId(),
                elementTemplateInfo.getVersion(),
                json,
                TargetPlatform.C8
        );
    }

    private Property createInputParameterProp(ElementTemplatePropertyInfo info, InputValueNamingPolicy inputValueNamingPolicy) {
        var property = new Property()
                .setLabel("Input: %s".formatted(info.getLabel()))
                .setValue(switch (inputValueNamingPolicy) {
                    case EMPTY -> "=";
                    case ATTRIBUTE_NAME -> "=%s".formatted(info.getName());
                })
                .setType(info.getType().getType())
                .setChoices(null)
                .setBinding(new Binding()
                        .setType(Binding.Type.ZEEBE_INPUT)
                        .setName(info.getName()));

        if (!info.isNotEmpty()) {
            property.setConstraints(new Constraints()
                    .setNotEmpty(info.isNotEmpty()));
        }

        if (!info.isEditable()) {
            property.setEditable(info.isEditable());
        }

        return property;
    }

    private Property createOutputParameterProp(ElementTemplatePropertyInfo info) {
        var property = new Property()
                .setLabel("Output: %s".formatted(info.getLabel()))
                .setValue("%s".formatted(info.getName()))
                .setType(info.getType().getType())
                .setChoices(null)
                .setBinding(new Binding()
                        .setType(Binding.Type.ZEEBE_OUTPUT)
                        .setSource("=%s".formatted(info.getName())));

        if (!info.isNotEmpty()) {
            property.setConstraints(new Constraints().setNotEmpty(false));
        }

        if (!info.isEditable()) {
            property.setEditable(false);
        }

        return property;
    }
}
