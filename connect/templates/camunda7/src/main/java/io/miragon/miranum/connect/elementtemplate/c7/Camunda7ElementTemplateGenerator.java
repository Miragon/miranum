package io.miragon.miranum.connect.elementtemplate.c7;

import io.miragon.miranum.connect.elementtemplate.api.BPMNElementType;
import io.miragon.miranum.connect.elementtemplate.api.PropertyType;
import io.miragon.miranum.connect.elementtemplate.core.*;
import io.miragon.miranum.connect.c7.elementtemplates.gen.Binding;
import io.miragon.miranum.connect.c7.elementtemplates.gen.CamundaC7ElementTemplate;
import io.miragon.miranum.connect.c7.elementtemplates.gen.Constraints;
import io.miragon.miranum.connect.c7.elementtemplates.gen.Property;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
@AllArgsConstructor
public class Camunda7ElementTemplateGenerator implements ElementTemplateGenerator {

    Camunda7Configuration camunda7Configuration;

    @Override
    public ElementTemplateGenerationResult generate(ElementTemplateInfo elementTemplateInfo, InputValueNamingPolicy inputValueNamingPolicy) {
        var elementTemplate = new CamundaC7ElementTemplate()
                .withName(elementTemplateInfo.getName())
                .withId(elementTemplateInfo.getId())
                .withAppliesTo(Collections.singletonList(BPMNElementType.BPMN_SERVICE_TASK.getValue()));

        if (elementTemplateInfo.getVersion() > 0) {
            elementTemplate.setVersion((double) elementTemplateInfo.getVersion());
        }

        // Add external task property
        var implementationProperty = createExternalTaskProperty();
        elementTemplate.getProperties().add(implementationProperty);

        // Add property for the topic of the external task
        var implementationTopicProperty = createExternalTaskTopicProperty(elementTemplateInfo.getType());
        elementTemplate.getProperties().add(implementationTopicProperty);

        // Add asyncBefore property
        var asyncBeforeProperty = createAsyncBeforeProperty();
        elementTemplate.getProperties().add(asyncBeforeProperty);

        // Add asyncAfter property
        var asyncAfterProperty = createAsyncAfterProperty();
        elementTemplate.getProperties().add(asyncAfterProperty);

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

        var json = CamundaC7ElementTemplateConverter.toJsonString(elementTemplate);
        return new ElementTemplateGenerationResult(elementTemplateInfo.getId(), elementTemplateInfo.getVersion(), json, TargetPlatform.C7);
    }

    private Property createInputParameterProp(ElementTemplatePropertyInfo info, InputValueNamingPolicy inputValueNamingPolicy) {
        var property = new Property()
                .withLabel("Input: %s".formatted(info.getLabel()))
                .withValue(switch (inputValueNamingPolicy) {
                    case EMPTY -> "${}";
                    case ATTRIBUTE_NAME -> "${%s}".formatted(info.getName());
                })
                .withType(info.getType().getType())
                .withChoices(null)
                .withBinding(new Binding()
                        .withType(Binding.Type.CAMUNDA_INPUT_PARAMETER)
                        .withName(info.getName()));

        if (!info.isNotEmpty()) {
            property.setConstraints(new Constraints()
                    .withNotEmpty(info.isNotEmpty()));
        }

        if (!info.isEditable()) {
            property.setEditable(info.isEditable());
        }

        return property;
    }

    private Property createOutputParameterProp(ElementTemplatePropertyInfo info) {
        var property = new Property()
                .withLabel("Output: %s".formatted(info.getLabel()))
                .withValue(info.getName())
                .withType(info.getType().getType())
                .withChoices(null)
                .withBinding(new Binding()
                        .withType(Binding.Type.CAMUNDA_OUTPUT_PARAMETER)
                        .withSource("${%s}".formatted(info.getName())));

        if (!info.isNotEmpty()) {
            property.setConstraints(new Constraints()
                    .withNotEmpty(info.isNotEmpty()));
        }

        if (!info.isEditable()) {
            property.setEditable(info.isEditable());
        }

        return property;
    }

    private Property createExternalTaskProperty() {
        return new Property()
                .withLabel("Implementation Type")
                .withType(PropertyType.STRING.getType())
                .withValue("external")
                .withEditable(false)
                // We set the choices to null, because we don't want to have an empty
                // choices property in the JSON. This can be fixed by not creating an
                // empty list in the first place, but this is what got generated by the
                // jsonschema2pojo generator.
                .withChoices(null)
                .withBinding(new Binding()
                        .withType(Binding.Type.PROPERTY)
                        .withName("camunda:type"));
    }

    private Property createExternalTaskTopicProperty(String type) {
        return new Property()
                .withLabel("Topic")
                .withType(PropertyType.STRING.getType())
                .withValue(type)
                .withEditable(false)
                .withChoices(null)
                .withBinding(new Binding()
                        .withType(Binding.Type.PROPERTY)
                        .withName("camunda:topic"));
    }

    private Property createAsyncBeforeProperty() {
        return new Property()
                .withLabel("Async Before")
                .withType("Boolean")
                .withValue(camunda7Configuration.getAsyncBeforeDefaultValue())
                .withEditable(true)
                .withChoices(null)
                .withBinding(new Binding()
                        .withType(Binding.Type.PROPERTY)
                        .withName("camunda:asyncBefore"));
    }

    private Property createAsyncAfterProperty() {
        return new Property()
                .withLabel("Async After")
                .withType("Boolean")
                .withValue(camunda7Configuration.getAsyncAfterDefaultValue())
                .withEditable(true)
                .withChoices(null)
                .withBinding(new Binding()
                        .withType(Binding.Type.PROPERTY)
                        .withName("camunda:asyncAfter"));
    }
}
