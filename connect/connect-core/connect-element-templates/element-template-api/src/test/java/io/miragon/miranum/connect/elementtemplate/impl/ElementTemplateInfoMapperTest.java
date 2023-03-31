package io.miragon.miranum.connect.elementtemplate.impl;

import io.miragon.miranum.connect.elementtemplate.api.BPMNElementType;
import io.miragon.miranum.connect.elementtemplate.api.GenerateElementTemplate;
import io.miragon.miranum.connect.shared.TooManyParametersException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

class ElementTemplateInfoMapperTest {

    @Test
    void mapGenerateElementTemplate_withNullInputParameterTypes_returnsCorrectElementTemplateInfo() {
        ElementTemplateInfoMapper mapper = new ElementTemplateInfoMapper();
        Method method = getMethod("methodWithNoParameters");
        GenerateElementTemplate generateElementTemplate = method.getAnnotation(GenerateElementTemplate.class);
        ElementTemplateInfo expected = new ElementTemplateInfo(
                "name",
                "id",
                01,
                "type",
                new BPMNElementType[]{BPMNElementType.BPMN_SERVICE_TASK},
                null,
                void.class);
        Assertions.assertEquals(expected, mapper.map(generateElementTemplate, method));
    }

    @Test
    void mapGenerateElementTemplate_withSingleInputParameterType_returnsCorrectElementTempalteInfo() {
        ElementTemplateInfoMapper mapper = new ElementTemplateInfoMapper();
        Method method = getMethod("methodWithSingleParameter", String.class);
        GenerateElementTemplate generateElementTemplate = method.getAnnotation(GenerateElementTemplate.class);
        ElementTemplateInfo expected = new ElementTemplateInfo(
                "name",
                "id",
                01,
                "type",
                new BPMNElementType[]{BPMNElementType.BPMN_SEND_TASK},
                String.class,
                void.class);
        Assertions.assertEquals(expected, mapper.map(generateElementTemplate, method));
    }

    @Test
    void mapGenerateElementTemplate_withTooManyParameters_throwsTooManyParametersException() {
        ElementTemplateInfoMapper mapper = new ElementTemplateInfoMapper();
        Method method = getMethod("methodWithToManyParameters", String.class, Integer.class);
        GenerateElementTemplate generateElementTemplate = method.getAnnotation(GenerateElementTemplate.class);
        Assertions.assertThrows(TooManyParametersException.class, () -> {
            mapper.map(generateElementTemplate, method);
        });
    }

    private Method getMethod(String methodName, Class<?>... parameterTypes) {
        try {
            return getClass().getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @GenerateElementTemplate(name = "name", id = "id", version = 01, type = "type")
    public void methodWithNoParameters() {}

    @GenerateElementTemplate(name = "name", id = "id", version = 01, type = "type", appliesTo = {BPMNElementType.BPMN_SEND_TASK})
    public void methodWithSingleParameter(String parameter) {}

    @GenerateElementTemplate(name = "name", id = "id", version = 01, type = "type")
    public void methodWithToManyParameters(String parameter1, Integer parameter2) {}
}