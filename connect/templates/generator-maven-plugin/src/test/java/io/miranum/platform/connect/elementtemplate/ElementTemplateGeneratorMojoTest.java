package io.miranum.platform.connect.elementtemplate;

import io.miranum.platform.connect.elementtemplate.api.ElementTemplate;
import io.miranum.platform.connect.elementtemplate.core.TargetPlatform;
import io.miranum.platform.connect.worker.api.Worker;
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

            @Worker(type = "test")
            @ElementTemplate(name = "Test")
            public void test() {
            }
        }
        String filename = "test-0-1.json";

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

            @Worker(type = "test1")
            @ElementTemplate(name = "Test1")
            public void test1() {
            }

            @Worker(type = "test2")
            @ElementTemplate(name = "Test2")
            public void test2() {
            }
        }
        String filename1 = "test1-0-1.json";
        String filename2 = "test2-0-1.json";

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