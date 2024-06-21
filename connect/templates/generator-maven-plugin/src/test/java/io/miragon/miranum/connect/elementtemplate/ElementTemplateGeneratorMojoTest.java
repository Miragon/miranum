package io.miragon.miranum.connect.elementtemplate;

import io.miragon.miranum.connect.elementtemplate.api.ElementTemplate;
import io.miragon.miranum.connect.elementtemplate.core.TargetPlatform;
import io.miragon.miranum.connect.worker.api.Worker;
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
        mojo.skip = false;
        mojo.clean = false;
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
        mojo.targetPlatform = TargetPlatform.C7;

        when(project.getCompileClasspathElements()).thenReturn(Collections.singletonList("test"));

        // Act
        mojo.execute();

        // Assert that no files were generated
        assertFalse(mojo.outputDirectory.exists());
    }

    @Test
    public void testExecuteWithSingleAnnotatedMethod_ShouldGenerateElementTemplate() throws Exception {
        // Arrange
        mojo.targetPlatform = TargetPlatform.C8;

        // .class file gets generated in target/test-classes
        when(project.getCompileClasspathElements()).thenReturn(Collections.singletonList("target/test-classes"));

        // Create a test class with a method annotated with @GenerateElementTemplate
        class Test {

            @Worker(type = "test")
            @ElementTemplate(name = "Test")
            public void test() {
            }
        }
        String filename = "test.json";

        // Act
        mojo.execute();

        // Assert that file was generated for target
        assertTrue(new File(mojo.outputDirectory, filename).exists());
    }

    @Test
    public void testExecuteWithMultipleAnnotatedMethods_ShouldGenerateElementTemplate() throws Exception {
        // Arrange
        mojo.targetPlatform = TargetPlatform.C7;

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
        String filename1 = "test1.json";
        String filename2 = "test2.json";

        // Act
        mojo.execute();

        // Assert that files were generated for target
        assertTrue(new File(mojo.outputDirectory, filename1).exists());

        assertTrue(new File(mojo.outputDirectory, filename2).exists());
    }

    @Test
    public void testExecuteWithSingleAnnotatedMethod_ShouldGenerateElementTemplateWithVersion() throws Exception {
        // Arrange
        mojo.targetPlatform = TargetPlatform.C8;

        // .class file gets generated in target/test-classes
        when(project.getCompileClasspathElements()).thenReturn(Collections.singletonList("target/test-classes"));

        // Create a test class with a method annotated with @GenerateElementTemplate
        class Test {

            @Worker(type = "test")
            @ElementTemplate(name = "Test", version = 1)
            public void test() {
            }
        }
        String filename = "test-v1.json";

        // Act
        mojo.execute();

        // Assert that file was generated for target
        assertTrue(new File(mojo.outputDirectory, filename).exists());
    }

    @Test
    public  void testExecuteWithCleanTrueParameter_ShouldDeleteAllFilesInOutputDir() throws Exception {
        // Arrange
        mojo.targetPlatform = TargetPlatform.C8;
        mojo.clean = true;

        // Create a file in the output directory
        File file1 = new File(mojo.outputDirectory, "shouldBeDeleted.json");
        File file2 = new File(mojo.outputDirectory, "shouldBeDeleted2.json");
        var outputDir = mojo.outputDirectory.mkdirs();
        var createFile1 = file1.createNewFile();
        var createFile2 = file2.createNewFile();
        assertTrue(file1.exists());
        assertTrue(file2.exists());

        // .class file gets generated in target/test-classes
        when(project.getCompileClasspathElements()).thenReturn(Collections.singletonList("target/test-classes"));

        // Create a test class with a method annotated with @GenerateElementTemplate
        class Test {

            @Worker(type = "testCleanParam")
            @ElementTemplate(name = "Test")
            public void test() {
            }
        }
        String filename = "testCleanParam.json";

        // Act
        mojo.execute();

        // Assert that the "old" file was deleted and the "new" file was generated
        assertFalse(file1.exists());
        assertFalse(file2.exists());
        assertTrue(new File(mojo.outputDirectory, filename).exists());
    }
}
