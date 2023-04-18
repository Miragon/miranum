package io.miragon.miranum.connect.elementtemplate;

import io.miragon.miranum.connect.elementtemplate.api.BPMNElementType;
import io.miragon.miranum.connect.elementtemplate.api.GenerateElementTemplate;
import io.miragon.miranum.connect.elementtemplate.core.TargetPlatform;
import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ElementTemplateGeneratorMojoTest {

    @TempDir
    File tmpTargetDir;

    private ElementTemplateGeneratorMojo mojo;
    private MavenProject project;

    @BeforeEach
    public void setUp() {
        project = mock(MavenProject.class);
        mojo = new ElementTemplateGeneratorMojo();
        mojo.project = project;
        mojo.outputDirectory = new File(tmpTargetDir, "target");
    }

    @Test
    public void testExecuteWhenSkipIsTrue_ShouldSkipGeneration() throws Exception {
        // Arrange
        mojo.skip = true;

        // Act
        mojo.execute();

        // Assert
        verify(project, never()).getCompileClasspathElements();
    }

    @Test
    public void testExecuteWhenNoAnnotatedMethodsFound_ShouldNotGenerateElementTemplates() throws Exception {
        // Arrange
        mojo.skip = false;
        mojo.targetPlatforms = new TargetPlatform[]{TargetPlatform.camunda7};

        when(project.getCompileClasspathElements()).thenReturn(Collections.singletonList("test"));

        // Act
        mojo.execute();

        // Assert that no files were generated
        assertFalse(mojo.outputDirectory.exists());
    }

    @Test
    public void testExecuteWithSingleAnnotatedMethod_ShouldGenerateElementTemplate() throws Exception {
        // Arrange
        mojo.skip = false;
        mojo.targetPlatforms = new TargetPlatform[]{TargetPlatform.camunda7};

        // .class file gets generated in target/test-classes
        when(project.getCompileClasspathElements()).thenReturn(Collections.singletonList("target/test-classes"));

        // Create a test class with a method annotated with @GenerateElementTemplate
        class Test {

            @GenerateElementTemplate(
                    type = "test",
                    name = "Test",
                    id = "test",
                    appliesTo = BPMNElementType.BPMN_SERVICE_TASK,
                    version = "1-0")
            public void test() {
            }
        }
        String filename = "test-1-0.json";

        // Act
        mojo.execute();

        // Assert that file was generated for target
        File platformDirectory = new File(mojo.outputDirectory, TargetPlatform.camunda7.name());
        assertTrue(platformDirectory.exists());
        assertTrue(new File(platformDirectory, filename).exists());
    }

    @Test
    public void testExecuteWithMultipleAnnotatedMethods_ShouldGenerateElementTemplate() throws Exception {
        // Arrange
        mojo.skip = false;
        mojo.targetPlatforms = new TargetPlatform[]{TargetPlatform.camunda7};

        // .class file gets generated in target/test-classes
        when(project.getCompileClasspathElements()).thenReturn(Collections.singletonList("target/test-classes"));

        // Create a test class with a method annotated with @GenerateElementTemplate
        class Test {

            @GenerateElementTemplate(
                    type = "test1",
                    name = "Test1",
                    id = "test1",
                    appliesTo = BPMNElementType.BPMN_SERVICE_TASK,
                    version = "1-0")
            public void test1() {
            }

            @GenerateElementTemplate(
                    type = "test2",
                    name = "Test2",
                    id = "test2",
                    appliesTo = BPMNElementType.BPMN_SERVICE_TASK,
                    version = "1-0")
            public void test2() {
            }
        }
        String filename1 = "test1-1-0.json";
        String filename2 = "test2-1-0.json";

        // Act
        mojo.execute();

        // Assert that files were generated for target
        File platformDirectory = new File(mojo.outputDirectory, TargetPlatform.camunda7.name());
        assertTrue(platformDirectory.exists());
        assertTrue(new File(platformDirectory, filename1).exists());

        assertTrue(platformDirectory.exists());
        assertTrue(new File(platformDirectory, filename2).exists());
    }
}