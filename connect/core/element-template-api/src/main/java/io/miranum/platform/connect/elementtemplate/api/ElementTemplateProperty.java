package io.miranum.platform.connect.elementtemplate.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to customize the property generation during the element template generation.
 * Is only used in combination with {@link ElementTemplate}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ElementTemplateProperty {

    /**
     * The type of the property of type {@link PropertyType}.
     *
     * @return the type of the property
     */
    PropertyType type();

    /**
     * The label of the property.
     * Corresponds to the label property of the element template property.
     *
     * @return the label of the property
     */
    String label() default "";

    /**
     * Constraints the property to be not empty.
     * Adds a constraint to the element template property.
     *
     * @return true if the property should not be empty, false otherwise
     */
    boolean notEmpty() default false;

    /**
     * Constraints the property to be editable.
     *
     * @return true if the property should be editable, false otherwise
     */
    boolean editable() default true;
}