package io.miranum.platform.connect.elementtemplate.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to generate an element template from a worker definition.
 * The fields of the input and output parameter of the method can be annotated
 * with {@link ElementTemplateProperty} to gain additional control over the element
 * template generation.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ElementTemplate {

    /**
     * The name of the element template.
     * Corresponds to the name property of the element template.
     *
     * @return the name of the element template
     */
    String name();

    /**
     * The description of the element template.
     *
     * @return the description of the element template
     */
    String description() default "";

    /**
     * The version of the element template.
     *
     * @return the version of the element template
     */
    String version() default "0-1";
}
