package io.miranum.platform.connect.elementtemplate;

import io.miranum.platform.connect.elementtemplate.core.ElementTemplateGenerationResult;
import io.miranum.platform.connect.elementtemplate.core.ElementTemplateGenerator;
import io.miranum.platform.connect.elementtemplate.core.ElementTemplateInfoMapper;
import io.miranum.platform.connect.elementtemplate.core.TargetPlatform;
import io.miranum.platform.connect.worker.api.Worker;
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
import java.nio.file.Path;
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
     * All target platforms for which goal should be executed.
     */
    @Parameter(name = "targetPlatforms", property = "elementtemplategen.targetPlatforms", required = true)
    TargetPlatform[] targetPlatforms;

    @Override
    public void execute() throws MojoExecutionException {
        if (Objects.nonNull(skip) && skip) {
            getLog().info("Element-template generation is skipped.");
            return;
        }

        List<ElementTemplateGenerator> generators = ElementTemplateGeneratorsFactory.create(targetPlatforms);

        var annotatedMethods = getWorkerAnnotatedMethods();

        if (annotatedMethods.isEmpty()) {
            log.info("No methods annotated with @GenerateElementTemplate found.");
            return;
        }

        for (var generator : generators) {
            for (var method : annotatedMethods) {
                var data = ElementTemplateInfoMapper.map(method);

                var generationResult = generator.generate(data);

                saveElementTemplateToFile(generationResult);
            }
        }
    }

    private void saveElementTemplateToFile(ElementTemplateGenerationResult generationResult) {
        var path = Path.of(generationResult.getTargetPlatform().name(), generationResult.getFileName()).toString();
        var elementTemplate = new File(outputDirectory, path);
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