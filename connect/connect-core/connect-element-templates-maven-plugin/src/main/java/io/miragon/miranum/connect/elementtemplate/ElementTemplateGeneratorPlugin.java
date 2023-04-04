package io.miragon.miranum.connect.elementtemplate;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.Objects;

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

        log.info("*** Generate element templates END ***");
    }
}