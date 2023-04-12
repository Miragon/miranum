package io.miragon.miranum.connect.elementtemplate;

import io.miragon.miranum.connect.elementtemplate.api.GenerateElementTemplate;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Goal which generates element templates.
 */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class ElementTemplateGeneratorPlugin extends AbstractMojo {

    private final Log log = getLog();

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    @Parameter(property = "connect-element-template.generator.maven.plugin.output", defaultValue = "${project.build.directory}")
    private File outputDirectory;

    @Parameter(name = "skip", property = "elementtemplategen.skip", defaultValue = "false")
    private Boolean skip;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (Objects.nonNull(skip) && skip) {
            getLog().info("Element-template generation is skipped.");
            return;
        }

        log.info("*** Generate element templates BEGIN ***");

        try {
            List<String> classpathElements = project.getCompileClasspathElements();
            List<URL> classpathURLs = new ArrayList<>(classpathElements.size());

            for (String element : classpathElements) {
                try {
                    classpathURLs.add(new File(element).toURI().toURL());
                } catch (MalformedURLException e) {
                    throw new MojoExecutionException("Malformed classpath element: " + element, e);
                }
            }

            project.addCompileSourceRoot(outputDirectory.getAbsolutePath());

            URLClassLoader urlClassLoader = new URLClassLoader(classpathURLs.toArray(new URL[0]), getClass().getClassLoader());

            Reflections reflections = new Reflections(new ConfigurationBuilder()
                    .setUrls(ClasspathHelper.forClassLoader(urlClassLoader))
                    .addClassLoader(urlClassLoader)
                    .addScanners(new MethodAnnotationsScanner())); // Add MethodAnnotationsScanner

            Set<Method> annotatedMethods = reflections.getMethodsAnnotatedWith(GenerateElementTemplate.class);
            // Replace 'MyMethodAnnotation' with your custom method annotation class

            for (Method method : annotatedMethods) {
                // Process the annotated methods
                log.info("Found annotated method: " + method.getName());
            }
        } catch (DependencyResolutionRequiredException e) {
            throw new MojoExecutionException("Dependency resolution failed", e);
        }

        log.info("*** Generate element templates END ***");
    }
}