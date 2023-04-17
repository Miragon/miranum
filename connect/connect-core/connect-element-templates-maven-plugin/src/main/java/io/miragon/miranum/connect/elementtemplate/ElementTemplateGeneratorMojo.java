package io.miragon.miranum.connect.elementtemplate;

import io.miragon.miranum.connect.adapter.in.c7.elementtemplates.Camunda7ElementTemplateGenerator;
import io.miragon.miranum.connect.elementtemplate.api.GenerateElementTemplate;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
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
@Mojo(name = "generate", defaultPhase = LifecyclePhase.PROCESS_CLASSES, requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME)
public class ElementTemplateGeneratorMojo extends AbstractMojo {

    private final Log log = getLog();

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    @Parameter(property = "connect-element-template.generator.maven.plugin.output", defaultValue = "${project.build.directory}")
    private File outputDirectory;

    @Parameter(name = "skip", property = "elementtemplategen.skip", defaultValue = "false")
    private Boolean skip;

    @Parameter(name = "path", property = "elementtemplategen.path")
    private String path;

    @Parameter(name = "targetPlatforms", property = "elementtemplategen.targetPlatforms", required = true)
    private TargetPlatform[] targetPlatforms;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (Objects.nonNull(skip) && skip) {
            getLog().info("Element-template generation is skipped.");
            return;
        }

        log.info("*** Generate element templates BEGIN ***");

        var annotatedMethods = findAnnotatedMethods();

        for (var method : annotatedMethods) {
            getLog().info("Found annotated method: " + method);
        }

        log.info("*** Generate element templates END ***");
    }

    private Set<Method> findAnnotatedMethods() throws MojoExecutionException {
        List<URL> classpathURLs = new ArrayList<>();
        if (Objects.isNull(path)) {
            List<String> classpathElements;
            try {
                classpathElements = project.getCompileClasspathElements();
            } catch (DependencyResolutionRequiredException e) {
                throw new RuntimeException(e);
            }

            for (String element : classpathElements) {
                try {
                    classpathURLs.add(new File(element).toURI().toURL());
                } catch (MalformedURLException e) {
                    throw new MojoExecutionException("Malformed classpath element: " + element, e);
                }
            }
        }

        var urlClassLoader = Objects.isNull(path) ? new URLClassLoader(classpathURLs.toArray(new URL[0]), getClass().getClassLoader()) : null;

        var reflections = Objects.isNull(path) ?
                new Reflections(new ConfigurationBuilder()
                        .setUrls(ClasspathHelper.forClassLoader(urlClassLoader))
                        .addClassLoaders(urlClassLoader)
                        .addScanners(Scanners.MethodsAnnotated)) :
                new Reflections(new ConfigurationBuilder()
                        .setUrls(ClasspathHelper.forPackage(path))
                        .setScanners(Scanners.MethodsAnnotated));

        return reflections.getMethodsAnnotatedWith(GenerateElementTemplate.class);
    }
}