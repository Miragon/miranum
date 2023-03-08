package io.miragon.miranum.connect.elementtemplate.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GenerateElementTemplate {
    String name();
    String id();
    BPMNElementType[] appliesTo();
    String version();
}