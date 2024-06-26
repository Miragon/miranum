package io.miragon.miranum.connect.elementtemplate.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ElementTemplateInfo {

    private final String name;

    private final String description;

    private final String id;

    private final int version;

    private final String type;

    private final List<ElementTemplatePropertyInfo> inputProperties;

    private final List<ElementTemplatePropertyInfo> outputProperties;
}
