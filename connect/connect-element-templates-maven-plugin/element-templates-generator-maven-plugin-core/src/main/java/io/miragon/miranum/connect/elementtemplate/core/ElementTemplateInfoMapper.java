package io.miragon.miranum.connect.elementtemplate.core;

import io.miragon.miranum.connect.elementtemplate.api.GenerateElementTemplate;
import io.miragon.miranum.connect.shared.TooManyParametersException;

import java.lang.reflect.Method;

public class ElementTemplateInfoMapper {

    public static ElementTemplateInfo map(GenerateElementTemplate generateElementTemplate, final Method method) {

        final Class<?>[] inputParameterTypes = method.getParameterTypes();

        if (inputParameterTypes.length > 1) {
            throw new TooManyParametersException(generateElementTemplate);
        }

        final Class<?> inputParameter = inputParameterTypes.length == 0 ? null : inputParameterTypes[0];

        return new ElementTemplateInfo(
                generateElementTemplate.name(),
                generateElementTemplate.id(),
                generateElementTemplate.version(),
                generateElementTemplate.type(),
                generateElementTemplate.appliesTo(),
                inputParameter,
                method.getReturnType());
    }
}