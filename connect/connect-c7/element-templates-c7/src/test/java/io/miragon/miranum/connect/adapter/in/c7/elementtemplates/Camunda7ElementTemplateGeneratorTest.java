package io.miragon.miranum.connect.adapter.in.c7.elementtemplates;

import io.miragon.miranum.connect.elementtemplate.api.BPMNElementType;
import io.miragon.miranum.connect.elementtemplate.impl.ElementTemplateInfo;
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
                "1.0.0",
                "test",
                new BPMNElementType[]{BPMNElementType.BPMN_SERVICE_TASK},
                TestInput.class, TestOutput.class);

        var expectedJsonResult = Files.readString(Path.of("./src/test/resources/expected-test-element-template.json"));

        // Act
        var result = camunda7ElementTemplateGenerator.generate(elementTemplateInfo);

        // Assert
        assertNotNull(result.getJsonString());
        assertEquals(expectedJsonResult, result.getJsonString());
    }

    @Test
    void generateCamunda7ElementTemplate_withNoInputAndOutputTypes_shouldReturnValidOutputWithoutInputAndOutputProperties() throws IOException {
        // Arrange
        Camunda7ElementTemplateGenerator camunda7ElementTemplateGenerator = new Camunda7ElementTemplateGenerator();
        var elementTemplateInfo = new ElementTemplateInfo(
                "Test",
                "test",
                "1.0.0",
                "test",
                new BPMNElementType[]{BPMNElementType.BPMN_SERVICE_TASK},
                null, null);

        var expectedJsonResult = Files.readString(Path.of("./src/test/resources/expected-test-element-template-no-input-and-output.json"));

        // Act
        var result = camunda7ElementTemplateGenerator.generate(elementTemplateInfo);

        // Assert
        assertNotNull(result.getJsonString());
        assertEquals(expectedJsonResult, result.getJsonString());
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