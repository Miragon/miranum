package io.miranum.platform.connect.elementtemplates.c7;

import io.miranum.platform.connect.elementtemplate.c7.Camunda7ElementTemplateGenerator;
import io.miranum.platform.connect.elementtemplate.api.ElementTemplateProperty;
import io.miranum.platform.connect.elementtemplate.api.PropertyType;
import io.miranum.platform.connect.elementtemplate.core.ElementTemplateInfo;
import io.miranum.platform.connect.elementtemplate.core.ElementTemplateInfoMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class Camunda7ElementTemplateGeneratorTest {

    @Test
    void generateCamunda7ElementTemplate_withValidInput_shouldReturnValidOutputWithInputAndOutputProperties() throws IOException {
        // Arrange
        Camunda7ElementTemplateGenerator camunda7ElementTemplateGenerator = new Camunda7ElementTemplateGenerator();
        var elementTemplateInfo = new ElementTemplateInfo(
                "Test",
                "test",
                "test",
                "0-1",
                "test",
                ElementTemplateInfoMapper.mapTypeToElementTemplateInfos(TestInput.class),
                ElementTemplateInfoMapper.mapTypeToElementTemplateInfos(TestOutput.class));

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
                "test",
                "0-1",
                "test",
                new ArrayList<>(),
                new ArrayList<>());

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
                "test",
                "0-1",
                "test",
                ElementTemplateInfoMapper.mapTypeToElementTemplateInfos(TestInputWithElementTemplatePropertyAnnotation.class),
                ElementTemplateInfoMapper.mapTypeToElementTemplateInfos(TestOutput.class));

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
