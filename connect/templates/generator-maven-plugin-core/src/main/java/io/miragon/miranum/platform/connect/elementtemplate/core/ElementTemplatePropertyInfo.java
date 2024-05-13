package io.miragon.miranum.platform.connect.elementtemplate.core;

import io.miragon.miranum.platform.connect.elementtemplate.api.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ElementTemplatePropertyInfo {

    String label;

    String name;

    PropertyType type;

    boolean editable;

    boolean notEmpty;
}
