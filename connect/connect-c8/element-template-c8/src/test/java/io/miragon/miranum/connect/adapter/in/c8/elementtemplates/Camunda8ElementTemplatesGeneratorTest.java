package io.miragon.miranum.connect.adapter.in.c8.elementtemplates;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.miragon.miranum.connect.elementtemplate.api.BPMNElementType;
import io.miragon.miranum.connect.elementtemplate.api.ElementTemplateProperty;
import io.miragon.miranum.connect.elementtemplate.api.PropertyType;
import io.miragon.miranum.connect.elementtemplate.impl.ElementTemplateInfo;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Camunda8ElementTemplatesGeneratorTest
{
    @Test
    void generateCamunda8ElementTemplate_withValidInput_shouldReturnValidOutputWithInputAndOutputProperties() throws IOException
    {
        // Arrange
        Camunda8ElementTemplateGenerator camunda7ElementTemplateGenerator = new Camunda8ElementTemplateGenerator();
        var elementTemplateInfo = new ElementTemplateInfo(
                "Test",
                "test",
                "0-1",
                "test",
                new BPMNElementType[]{BPMNElementType.BPMN_SERVICE_TASK},
                TestInput.class, TestOutput.class);

        var expectedJsonResult = Files.readString(Path.of("./src/test/resources/expected-test-element-template.json"));

        // Act
        var result = camunda7ElementTemplateGenerator.generate(elementTemplateInfo);

        // Create JsonNodes because the order of the properties is irrelevant
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode expectedJsonObject = objectMapper.readTree(expectedJsonResult);
        JsonNode generatedJsonObject = objectMapper.readTree(result.getJsonString());

        // Assert
        assertNotNull(generatedJsonObject);
        assertEquals(expectedJsonObject, generatedJsonObject);
    }

    @Test
    void generateCamunda7ElementTemplate_withNoInputAndOutputTypes_shouldReturnValidOutputWithoutInputAndOutputProperties() throws IOException
    {
        // Arrange
        Camunda8ElementTemplateGenerator camunda7ElementTemplateGenerator = new Camunda8ElementTemplateGenerator();
        var elementTemplateInfo = new ElementTemplateInfo(
                "Test",
                "test",
                "0-1",
                "test",
                new BPMNElementType[]{BPMNElementType.BPMN_SERVICE_TASK},
                null, null);

        var expectedJsonResult = Files.readString(Path.of("./src/test/resources/expected-test-element-template-no-input-and-output.json"));

        // Act
        var result = camunda7ElementTemplateGenerator.generate(elementTemplateInfo);

        // Create JsonNodes because the order of the properties is irrelevant
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode expectedJsonObject = objectMapper.readTree(expectedJsonResult);
        JsonNode generatedJsonObject = objectMapper.readTree(result.getJsonString());

        // Assert
        assertNotNull(generatedJsonObject);
        assertEquals(expectedJsonObject, generatedJsonObject);
    }

    @Test
    void generateCamunda7ElementTemplate_withAnnotatedInputProperties_shouldReturnValidOutputWithInputAndOutputProperties() throws IOException
    {
        // Arrange
        Camunda8ElementTemplateGenerator camunda7ElementTemplateGenerator = new Camunda8ElementTemplateGenerator();
        var elementTemplateInfo = new ElementTemplateInfo(
                "Test",
                "test",
                "0-1",
                "test",
                new BPMNElementType[]{BPMNElementType.BPMN_SERVICE_TASK},
                TestInputWithElementTemplatePropertyAnnotation.class, TestOutput.class);

        var expectedJsonResult = Files.readString(Path.of("./src/test/resources/expected-test-element-template-with-annotated-input-properties.json"));

        // Act
        var result = camunda7ElementTemplateGenerator.generate(elementTemplateInfo);

        // Create JsonNodes because the order of the properties is irrelevant
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode expectedJsonObject = objectMapper.readTree(expectedJsonResult);
        JsonNode generatedJsonObject = objectMapper.readTree(result.getJsonString());

        // Assert
        assertNotNull(generatedJsonObject);
        assertEquals(expectedJsonObject, generatedJsonObject);
    }

    private static class TestInputWithElementTemplatePropertyAnnotation
    {
        @ElementTemplateProperty(label = "Property1", type = PropertyType.TEXT, notEmpty = true)
        private String name;
        @ElementTemplateProperty(label = "Property2", type = PropertyType.STRING)
        private String id;
    }

    private static class TestInput
    {
        private String name;
        private String id;
    }

    private static class TestOutput
    {
        private String name;
        private String id;
    }
}
