package io.miranum.platform.connect.elementtemplate.core;

import io.miranum.platform.connect.elementtemplate.api.ElementTemplate;
import io.miranum.platform.connect.elementtemplate.api.ElementTemplateProperty;
import io.miranum.platform.connect.elementtemplate.api.PropertyType;
import io.miranum.platform.connect.worker.api.Worker;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ElementTemplateInfoMapper {

    public static ElementTemplateInfo map(Method method) {
        final Class<?>[] inputParameterTypes = method.getParameterTypes();

        if (inputParameterTypes.length > 1) {
            throw new IllegalArgumentException("Too many parameters");
        }

        final Class<?> inputParameter = inputParameterTypes.length == 0 ? null : inputParameterTypes[0];
        final Class<?> returnType = method.getReturnType();

        var worker = method.getAnnotation(Worker.class);
        var elementTemplate = method.getAnnotation(ElementTemplate.class);

        var inputProperties = Objects.nonNull(inputParameter)
                ? mapTypeToElementTemplateInfos(inputParameter)
                : new ArrayList<ElementTemplatePropertyInfo>();
        var outputProperties = mapTypeToElementTemplateInfos(returnType);

        return new ElementTemplateInfo(
                Objects.nonNull(elementTemplate) ? elementTemplate.name() : method.getName(),
                Objects.nonNull(elementTemplate) ? elementTemplate.description() : null,
                worker.type(),
                Objects.nonNull(elementTemplate) ? elementTemplate.version() : "0-1",
                worker.type(),
                inputProperties,
                outputProperties
        );
    }

    public static List<ElementTemplatePropertyInfo> mapTypeToElementTemplateInfos(Class<?> type) {
        var elementTemplatePropertyInfos = new ArrayList<ElementTemplatePropertyInfo>();
        for (var property : type.getDeclaredFields()) {
            var annotation = property.getAnnotation(ElementTemplateProperty.class);

            var elementTemplatePropertyInfo = new ElementTemplatePropertyInfo(
                    Objects.nonNull(annotation) ? annotation.label() : property.getName(),
                    property.getName(),
                    Objects.nonNull(annotation) ? annotation.type() : PropertyType.STRING,
                    !Objects.nonNull(annotation) || annotation.editable(),
                    !Objects.nonNull(annotation) || annotation.notEmpty()
            );

            elementTemplatePropertyInfos.add(elementTemplatePropertyInfo);
        }
        return elementTemplatePropertyInfos;
    }
}
