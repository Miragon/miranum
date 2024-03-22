package io.miranum.platform.connect.elementtemplate.c8;

import io.miranum.platform.connect.elementtemplate.c8.schema.Binding;
import io.miranum.platform.connect.elementtemplate.c8.schema.Constraints;
import io.miranum.platform.connect.elementtemplate.c8.schema.Property;
import io.miranum.platform.connect.elementtemplate.api.BPMNElementType;
import io.miranum.platform.connect.elementtemplate.api.PropertyType;
import io.miranum.platform.connect.elementtemplate.c8.schema.CamundaC8ElementTemplate;
import io.miranum.platform.connect.elementtemplate.core.*;
import lombok.extern.java.Log;

import java.util.List;

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
        for (var inputProperty : elementTemplateInfo.getInputProperties()) {
            var property = createPropertyWithValue(inputProperty, false);
            elementTemplate.getProperties().add(property);
        }

        // Add properties for output parameters
        for (var outputProperties : elementTemplateInfo.getOutputProperties()) {
            var property = createPropertyWithValue(outputProperties, true);
            elementTemplate.getProperties().add(property);
        }

        var json = CamundaC8ElementTemplateConverter.toJsonString(elementTemplate);
        return new ElementTemplateGenerationResult(
                elementTemplateInfo.getId(),
                elementTemplateInfo.getVersion(),
                json,
                TargetPlatform.camunda8
        );
    }

    private Property createPropertyWithValue(ElementTemplatePropertyInfo info, boolean output) {
        var value = output ? info.getLabel() + "Result" : "=";
        var bindingType = output ? Binding.Type.ZEEBE_OUTPUT : Binding.Type.ZEEBE_INPUT;
        var property = new Property()
                .setLabel(info.getLabel())
                .setType(info.getType().getType())
                .setChoices(null)
                .setBinding(new Binding()
                        .setType(bindingType))
                .setValue(value);

        if (output) {
            property.getBinding().setSource("=" + info.getLabel());
        } else {
            property.getBinding().setName(info.getName());
        }

        // Only set if false, else jackson will display default value in generated json
        if (!info.isNotEmpty()) {
            property.setConstraints(new Constraints()
                    .setNotEmpty(info.isNotEmpty()));
        }

        if (!info.isEditable()) {
            property.setEditable(info.isEditable());
        }

        return property;
    }
}
