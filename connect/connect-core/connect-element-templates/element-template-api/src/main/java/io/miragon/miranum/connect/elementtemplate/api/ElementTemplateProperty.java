package io.miragon.miranum.connect.elementtemplate.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ElementTemplateProperty {

    PropertyType type();
    String label() default "";
    boolean notEmpty() default false;
    boolean editable() default true;
}