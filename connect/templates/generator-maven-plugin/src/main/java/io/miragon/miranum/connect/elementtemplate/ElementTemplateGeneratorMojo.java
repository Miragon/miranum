package io.miragon.miranum.connect.elementtemplate;

import io.miragon.miranum.connect.elementtemplate.core.*;
import io.miragon.miranum.connect.worker.api.Worker;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A maven mojo for generating element templates.
 */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.PROCESS_CLASSES, requiresDependencyResolution = ResolutionScope.RUNTIME)
public class ElementTemplateGeneratorMojo extends AbstractMojo {

    private final Log log = getLog();

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    /**
     * Directory to output the generated element templates to.
     */
    @Parameter(property = "elementtemplategen.outputDirectory", defaultValue = "${project.build.directory/generated-sources/element-templates}")
    File outputDirectory;

    /**
     * A flag indicating if the generation should be skipped.
     */
    @Parameter(name = "skip", property = "elementtemplategen.skip", defaultValue = "false")
    Boolean skip;

    /**
     * The target platform for which goal should be executed.
     */
    @Parameter(name = "targetPlatform", property = "elementtemplategen.targetPlatform", required = true)
    TargetPlatform targetPlatform;

    /**
     * The naming policy for input values.
     * EMPTY => ${}
     * ATTRIBUTE_NAME => ${<attributeName>}
     */
    @Parameter(name = "inputValueNamingPolicy", property = "elementtemplategen.inputValueNamingPolicy", defaultValue = "EMPTY")
    InputValueNamingPolicy inputValueNamingPolicy;


    @Override
    public void execute() throws MojoExecutionException {
        if (Objects.nonNull(skip) && skip) {
            getLog().info("Element-Template generation is skipped.");
            return;
        } else if (Objects.isNull(targetPlatform)) {
            getLog().info("Element-Template generation failed. Please configure a target platform. Valid target platforms are: camunda7 or Camunda8");
            return;
        }

        ElementTemplateGenerator generator = ElementTemplateGeneratorsFactory.create(targetPlatform);

        var annotatedMethods = getWorkerAnnotatedMethods();

        if (annotatedMethods.isEmpty()) {
            log.info("No methods annotated with @GenerateElementTemplate found.");
            return;
        }

        for (var method : annotatedMethods) {
            var data = ElementTemplateInfoMapper.map(method);

            var generationResult = generator.generate(data, inputValueNamingPolicy);

            saveElementTemplateToFile(generationResult);
        }
    }

    private void saveElementTemplateToFile(ElementTemplateGenerationResult generationResult) {
        var elementTemplate = new File(outputDirectory, generationResult.getFileName());
        boolean dirsCreated = elementTemplate.getParentFile().mkdirs();
        try {
            var fileCreated = elementTemplate.createNewFile();
            Files.writeString(elementTemplate.toPath(), generationResult.getJson());
            log.info("Element template file created: " + elementTemplate.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets all methods annotated with {@link Worker}.
     *
     * @return a set of methods annotated with {@link Worker}.
     * @throws MojoExecutionException if the classpath elements could not be resolved.
     */
    private Set<Method> getWorkerAnnotatedMethods() throws MojoExecutionException {
        List<String> classpathElements;
        try {
            classpathElements = project.getCompileClasspathElements();
        } catch (DependencyResolutionRequiredException e) {
            throw new RuntimeException(e);
        }

        List<URL> classpathURLs = new ArrayList<>();
        for (String element : classpathElements) {
            try {
                classpathURLs.add(new File(element).toURI().toURL());
            } catch (MalformedURLException e) {
                throw new MojoExecutionException("Malformed classpath element: " + element, e);
            }
        }

        var urlClassLoader = new URLClassLoader(classpathURLs.toArray(new URL[0]), getClass().getClassLoader());

        var reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forClassLoader(urlClassLoader))
                .addClassLoaders(urlClassLoader)
                .addScanners(Scanners.MethodsAnnotated));

        return reflections.getMethodsAnnotatedWith(Worker.class);
    }
}
