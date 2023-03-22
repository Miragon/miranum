package io.miragon.miranum.connect.elementtemplate.api;

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
public @interface GenerateElementTemplate {

    /**
     * The name of the element template.
     * Corresponds to the name property of the element template.
     * @return the name of the element template
     */
    String name();
    /**
     * The id of the element template.
     * Corresponds to the id property of the element template.
     * @return the id of the element template
     */
    String id();
    /**
     * The type of the element template.
     * Corresponds to the topic of the external task.
     * @return the description of the element template
     */
    String type();
    /**
     * The BPMN element types to which the element template applies.
     * Corresponds to the appliesTo property of the element template.
     * @return the BPMN element types to which the element template applies
     */
    BPMNElementType[] appliesTo() default {BPMNElementType.BPMN_SERVICE_TASK};
    /**
     * The version of the element template.
     * Is only used to generate the file name of the element template and
     * is therefore not included in the element template.
     * @return the version of the element template
     */
    double version();
}