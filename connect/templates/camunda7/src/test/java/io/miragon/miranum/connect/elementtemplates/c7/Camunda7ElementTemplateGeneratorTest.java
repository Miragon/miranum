package io.miragon.miranum.connect.elementtemplates.c7;

import io.miragon.miranum.connect.elementtemplate.api.ElementTemplateProperty;
import io.miragon.miranum.connect.elementtemplate.api.PropertyType;
import io.miragon.miranum.connect.elementtemplate.c7.Camunda7ElementTemplateGenerator;
import io.miragon.miranum.connect.elementtemplate.core.Camunda7Configuration;
import io.miragon.miranum.connect.elementtemplate.core.ElementTemplateInfo;
import io.miragon.miranum.connect.elementtemplate.core.ElementTemplateInfoMapper;
import io.miragon.miranum.connect.elementtemplate.core.InputValueNamingPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Camunda7ElementTemplateGeneratorTest {

    Camunda7ElementTemplateGenerator camunda7ElementTemplateGenerator;

    @BeforeEach
    void setUp() {
        Camunda7Configuration config = new Camunda7Configuration() {
            @Override
            public Boolean getAsyncBeforeDefaultValue() {
                return true;
            }

            @Override
            public Boolean getAsyncAfterDefaultValue() {
                return false;
            }
        };

        camunda7ElementTemplateGenerator = new Camunda7ElementTemplateGenerator(config);
    }

    @Test
    void generateCamunda7ElementTemplate_withValidInput_shouldReturnValidOutputWithInputAndOutputProperties() throws IOException {
        // Arrange
        var elementTemplateInfo = new ElementTemplateInfo(
                "Test",
                "test",
                "test",
                -1,
                "test",
                ElementTemplateInfoMapper.mapTypeToElementTemplateInfos(TestInput.class),
                ElementTemplateInfoMapper.mapTypeToElementTemplateInfos(TestOutput.class));

        var expectedJsonResult = Files.readString(Path.of("./src/test/resources/expected-test-element-template.json"));

        // Act
        var result = camunda7ElementTemplateGenerator.generate(elementTemplateInfo, InputValueNamingPolicy.EMPTY);

        // Assert
        assertNotNull(result.getJson());
        assertTrue(compareStrings(expectedJsonResult, result.getJson()));
    }

    @Test
    void generateCamunda7ElementTemplate_withNoInputAndOutputTypes_shouldReturnValidOutputWithoutInputAndOutputProperties() throws IOException {
        // Arrange
        var elementTemplateInfo = new ElementTemplateInfo(
                "Test",
                "test",
                "test",
                -1,
                "test",
                new ArrayList<>(),
                new ArrayList<>());

        var expectedJsonResult = Files.readString(Path.of("./src/test/resources/expected-test-element-template-no-input-and-output.json"));

        // Act
        var result = camunda7ElementTemplateGenerator.generate(elementTemplateInfo, InputValueNamingPolicy.EMPTY);

        // Assert
        assertNotNull(result.getJson());
        assertTrue(compareStrings(expectedJsonResult, result.getJson()));
    }

    @Test
    void generateCamunda7ElementTemplate_withAnnotatedInputProperties_shouldReturnValidOutputWithInputAndOutputProperties() throws IOException {
        // Arrange
        var elementTemplateInfo = new ElementTemplateInfo(
                "Test",
                "test",
                "test",
                -1,
                "test",
                ElementTemplateInfoMapper.mapTypeToElementTemplateInfos(TestInputWithElementTemplatePropertyAnnotation.class),
                ElementTemplateInfoMapper.mapTypeToElementTemplateInfos(TestOutput.class));

        var expectedJsonResult = Files.readString(Path.of("./src/test/resources/expected-test-element-template-with-annotated-input-properties.json"));

        // Act
        var result = camunda7ElementTemplateGenerator.generate(elementTemplateInfo, InputValueNamingPolicy.EMPTY);

        // Assert
        assertNotNull(result.getJson());
        assertTrue(compareStrings(expectedJsonResult, result.getJson()));
    }

    @Test
    void generateCamunda7ElementTemplate_withNoInputAndOutputTypes_shouldReturnValidOutputWithVersion() throws IOException {
        // Arrange
        var elementTemplateInfo = new ElementTemplateInfo(
                "Test",
                "test",
                "test",
                1,
                "test",
                new ArrayList<>(),
                new ArrayList<>()
        );

        var expectedJsonResult = Files.readString(Path.of("./src/test/resources/expected-test-element-template-with-version.json"));

        // Act
        var result = camunda7ElementTemplateGenerator.generate(elementTemplateInfo, InputValueNamingPolicy.EMPTY);

        // Assert
        assertNotNull(result.getJson());
        assertTrue(compareStrings(expectedJsonResult, result.getJson()));
    }

    @Test
    void generateCamunda7ElementTemplate_withValidInput_shouldReturnValidOutputWithNamedInputValues() throws IOException {
        // Arrange
        var elementTemplateInfo = new ElementTemplateInfo(
                "Test",
                "test",
                "test",
                -1,
                "test",
                ElementTemplateInfoMapper.mapTypeToElementTemplateInfos(TestInput.class),
                new ArrayList<>()
        );

        var expectedJsonResult = Files.readString(Path.of("./src/test/resources/expected-test-element-template-with-named-input-values.json"));

        // Act
        var result = camunda7ElementTemplateGenerator.generate(elementTemplateInfo, InputValueNamingPolicy.ATTRIBUTE_NAME);

        // Assert
        assertNotNull(result.getJson());
        assertTrue(compareStrings(expectedJsonResult, result.getJson()));
    }

    private Boolean compareStrings(String expected, String actual) {
        var expectedTrimmed = expected.replaceAll("\\s", "");
        var actualTrimmed = actual.replaceAll("\\s", "");
        return expectedTrimmed.equals(actualTrimmed);
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
