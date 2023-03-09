package io.miragon.miranum.connect.elementtemplate.impl;

import io.miragon.miranum.connect.shared.ToManyParametersException;
import io.miragon.miranum.connect.elementtemplate.api.GenerateElementTemplate;

import java.lang.reflect.Method;

public class ElementTemplateInfoMapper {

    public ElementTemplateInfo map(GenerateElementTemplate generateElementTemplate, final Method method) {

        final Class<?>[] inputParameterTypes = method.getParameterTypes();

        if (inputParameterTypes.length > 1) {
            throw new ToManyParametersException(generateElementTemplate);
        }

        final Class<?> inputParameter = inputParameterTypes.length == 0 ? null : inputParameterTypes[0];

        return new ElementTemplateInfo(generateElementTemplate.name(),
                generateElementTemplate.id(),
                generateElementTemplate.version(),
                generateElementTemplate.type(),
                generateElementTemplate.appliesTo(),
                inputParameter,
                method.getReturnType());
    }
}