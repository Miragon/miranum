package io.miragon.miranum.connect.adapter.in.c7.elementtemplates;

import io.miragon.miranum.connect.elementtemplate.api.BPMNElementType;
import io.miragon.miranum.connect.elementtemplate.api.ElementTemplateProperty;
import io.miragon.miranum.connect.elementtemplate.api.PropertyType;
import io.miragon.miranum.connect.elementtemplate.core.ElementTemplateInfo;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
class Camunda7ElementTemplateGeneratorTest {

    @Test
    void generateCamunda7ElementTemplate_withValidInput_shouldReturnValidOutputWithInputAndOutputProperties() throws IOException {
        // Arrange
        Camunda7ElementTemplateGenerator camunda7ElementTemplateGenerator = new Camunda7ElementTemplateGenerator();
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

        // Assert
        assertNotNull(result.getJson());
        assertEquals(expectedJsonResult, result.getJson());
    }

    @Test
    void generateCamunda7ElementTemplate_withNoInputAndOutputTypes_shouldReturnValidOutputWithoutInputAndOutputProperties() throws IOException {
        // Arrange
        Camunda7ElementTemplateGenerator camunda7ElementTemplateGenerator = new Camunda7ElementTemplateGenerator();
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

        // Assert
        assertNotNull(result.getJson());
        assertEquals(expectedJsonResult, result.getJson());
    }

    @Test
    void generateCamunda7ElementTemplate_withAnnotatedInputProperties_shouldReturnValidOutputWithInputAndOutputProperties() throws IOException {
        // Arrange
        Camunda7ElementTemplateGenerator camunda7ElementTemplateGenerator = new Camunda7ElementTemplateGenerator();
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

        // Assert
        assertNotNull(result.getJson());
        assertEquals(expectedJsonResult, result.getJson());
    }

    private static class TestInputWithElementTemplatePropertyAnnotation {
        @ElementTemplateProperty(label = "Property1", type = PropertyType.TEXT, notEmpty = true)
        private String name;
        @ElementTemplateProperty(label = "Property2", type = PropertyType.STRING)
        private String id;
    }

    private static class TestInput {
        private String name;
        private String id;
    }
    private static class TestOutput {
        private String name;
        private String id;
    }
}