package io.miragon.miranum.connect.elementtemplate;

import io.miragon.miranum.connect.elementtemplate.api.ElementTemplateInfo;
import io.miragon.miranum.connect.elementtemplate.api.GenerateElementTemplate;
import io.miragon.miranum.connect.shared.TooManyParametersException;

import java.lang.reflect.Method;

public class ElementTemplateInfoMapper {

    public ElementTemplateInfo map(GenerateElementTemplate generateElementTemplate, final Method method) {

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